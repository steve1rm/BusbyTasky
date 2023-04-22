package me.androidbox.presentation.event.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.androidbox.component.event.VisitorInfo
import me.androidbox.domain.DateTimeFormatterProvider.toZoneDateTime
import me.androidbox.presentation.agenda.constant.AgendaMenuActionType
import me.androidbox.domain.agenda.model.Attendee
import me.androidbox.domain.agenda.model.Event
import me.androidbox.domain.agenda.usecase.UsersInitialsExtractionUseCase
import me.androidbox.domain.alarm_manager.AgendaType
import me.androidbox.domain.alarm_manager.AlarmScheduler
import me.androidbox.domain.alarm_manager.toAlarmItem
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.authentication.preference.PreferenceRepository
import me.androidbox.domain.authentication.remote.EventRepository
import me.androidbox.domain.event.usecase.VerifyVisitorEmailUseCase
import me.androidbox.domain.work_manager.UploadEvent
import me.androidbox.presentation.alarm_manager.AlarmReminderProvider
import me.androidbox.presentation.event.screen.EventScreenEvent
import me.androidbox.presentation.event.screen.EventScreenState
import me.androidbox.presentation.mapper.toVisitorInfo
import me.androidbox.presentation.navigation.Screen.EventScreen.EVENT_ID
import me.androidbox.presentation.navigation.Screen.EventScreen.MENU_ACTION_TYPE
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val usersInitialsExtractionUseCase: UsersInitialsExtractionUseCase,
    private val verifyVisitorEmailUseCase: VerifyVisitorEmailUseCase,
    private val preferenceRepository: PreferenceRepository,
    private val alarmScheduler: AlarmScheduler,
    private val uploadEvent: UploadEvent,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _eventScreenState: MutableStateFlow<EventScreenState> = MutableStateFlow(EventScreenState())
    val eventScreenState = _eventScreenState.asStateFlow()

    init {
        val menuActionType = savedStateHandle.get<String>(MENU_ACTION_TYPE)
        val eventId = savedStateHandle.get<String>(EVENT_ID) ?: ""

        menuActionType?.let { actionType ->
            when (actionType) {
                AgendaMenuActionType.OPEN.name -> {
                    _eventScreenState.update { eventScreenState ->
                        eventScreenState.copy(
                            isEditMode = false,
                            eventId = eventId
                        )
                    }
                    fetchEventById(eventId)
                }
                AgendaMenuActionType.EDIT.name -> {
                    _eventScreenState.update { eventScreenState ->
                        eventScreenState.copy(
                            isEditMode = true,
                            eventId = eventId
                        )
                    }
                    fetchEventById(eventId)
                }
                else -> {
                    _eventScreenState.update { eventScreenState ->
                        eventScreenState.copy(
                            isEditMode = false,
                            eventId = UUID.randomUUID().toString()
                        )
                    }
                }
            }
        }
    }

    fun onEventScreenEvent(eventScreenEvent: EventScreenEvent) {
        when(eventScreenEvent) {
            is EventScreenEvent.OnPhotoUriAdded -> {
                _eventScreenState.update { eventScreenState ->
                    eventScreenState.copy(
                        listOfPhotoUri = eventScreenState.listOfPhotoUri + eventScreenEvent.photoUri
                    )
                }
            }
            is EventScreenEvent.OnSaveTitleOrDescription -> {
                _eventScreenState.update { eventScreenState ->
                    eventScreenState.copy(
                        eventTitle = eventScreenEvent.title,
                        eventDescription = eventScreenEvent.description
                    )
                }
            }
            is EventScreenEvent.OnDeleteVisitor -> {
                val visitorInfo = eventScreenState.value.visitors.firstOrNull { visitorInfo ->
                    visitorInfo.userId == eventScreenEvent.visitorInfo.userId
                }

                val attendee = eventScreenState.value.attendees.firstOrNull { attendee ->
                    attendee.userId == eventScreenEvent.visitorInfo.userId
                }

                if(visitorInfo != null) {
                    _eventScreenState.update { eventScreenState ->
                        eventScreenState.copy(
                            visitors = eventScreenState.visitors - visitorInfo
                        )
                    }
                }

                if(attendee != null) {
                    _eventScreenState.update { eventScreenState ->
                        eventScreenState.copy(
                            attendees = eventScreenState.attendees - attendee
                        )
                    }
                }
            }
            is EventScreenEvent.OnSelectedAgendaAction -> {
                _eventScreenState.update { eventScreenState ->
                    eventScreenState.copy(
                        selectedAgendaActionType = eventScreenEvent.agendaActionType
                    )
                }
            }
            is EventScreenEvent.OnSaveEventDetails -> {
                insertEventDetails(eventScreenState.value.eventId)
            }
            is EventScreenEvent.OnStartTimeDuration -> {
                _eventScreenState.update { eventScreenState ->
                    eventScreenState.copy(
                        startTime = eventScreenEvent.startTime,
                    )
                }
            }
            is EventScreenEvent.OnStartDateDuration -> {
                _eventScreenState.update { eventScreenState ->
                    eventScreenState.copy(
                        startDate = eventScreenEvent.startDate,
                    )
                }
            }
            is EventScreenEvent.OnEndTimeDuration -> {
                _eventScreenState.update { eventScreenState ->
                    eventScreenState.copy(
                        endTime = eventScreenEvent.endTime,
                    )
                }
            }
            is EventScreenEvent.OnEndDateDuration -> {
                _eventScreenState.update { eventScreenState ->
                    eventScreenState.copy(
                        endDate = eventScreenEvent.endDate,
                    )
                }
            }
            is EventScreenEvent.OnStartDateTimeChanged -> {
                _eventScreenState.update { eventScreenState ->
                    eventScreenState.copy(
                        isStartDateTime = eventScreenEvent.isStartDateTime
                    )
                }
            }
            is EventScreenEvent.OnShowAlarmReminderDropdown -> {
                _eventScreenState.update { eventScreenState ->
                    eventScreenState.copy(
                        shouldOpenDropdown = eventScreenEvent.shouldOpen
                    )
                }
            }
            is EventScreenEvent.OnAlarmReminderChanged -> {
                _eventScreenState.update { eventScreenState ->
                    eventScreenState.copy(
                        alarmReminderItem = eventScreenEvent.reminderItem
                    )
                }
            }
            is EventScreenEvent.OnAttendeeAdded -> {
                _eventScreenState.update { eventScreenState ->
                    val currentUserId = preferenceRepository.retrieveCurrentUserOrNull()?.userId
                    val initials = usersInitialsExtractionUseCase.execute(eventScreenEvent.attendee.fullName)

                    val visitorInfo = eventScreenEvent.attendee.toVisitorInfo(
                        initials = initials,
                        isCreator = currentUserId == eventScreenEvent.attendee.userId
                    )

                    eventScreenState.copy(
                        attendees = eventScreenState.attendees + eventScreenEvent.attendee,
                        visitors = eventScreenState.visitors + visitorInfo
                    )
                }
            }
            is EventScreenEvent.OnVisitorEmailChanged -> {
                _eventScreenState.update { eventScreenState ->
                    eventScreenState.copy(
                        visitorEmail = eventScreenEvent.visitorEmail
                    )
                }
            }
            is EventScreenEvent.OnShowVisitorDialog -> {
                _eventScreenState.update { eventScreenState ->
                    eventScreenState.copy(
                        shouldShowVisitorDialog = eventScreenEvent.shouldShowVisitorDialog,
                        isEmailVerified = true,
                        visitorEmail = ""
                    )
                }
            }
            is EventScreenEvent.CheckVisitorExists -> {
                verifyVisitorEmail(eventScreenEvent.visitorEmail)
            }
            is EventScreenEvent.OnShowDeleteEventAlertDialog -> {
                _eventScreenState.update { eventScreenState ->
                    eventScreenState.copy(
                        shouldShowDeleteAlertDialog = eventScreenEvent.shouldShowDeleteAlertDialog
                    )
                }
            }
            is EventScreenEvent.OnDeleteEvent -> {
                deleteEvent(eventScreenEvent.eventId)
            }

            is EventScreenEvent.OnVisitorFilterTypeChanged -> {
                _eventScreenState.update {  eventScreenState ->
                    eventScreenState.copy(
                        selectedVisitorFilterType = eventScreenEvent.visitorFilterType
                    )
                }
            }

            is EventScreenEvent.OnAttendeeDeleted -> TODO()
        }
    }

    private fun verifyVisitorEmail(visitorEmail: String) {
        viewModelScope.launch {
            val responseState = verifyVisitorEmailUseCase.execute(visitorEmail)

            when(responseState) {
                is ResponseState.Loading -> {
                    /* TODO Show loading */
                }
                is ResponseState.Success -> {
                    responseState.data?.let { _attendee: Attendee ->
                        val attendee = _attendee.copy(
                            email = _attendee.email,
                            fullName = _attendee.fullName,
                            userId = _attendee.userId,
                            remindAt = _attendee.remindAt,
                            eventId = eventScreenState.value.eventId,
                            isGoing = _attendee.isGoing
                        )

                        onEventScreenEvent(EventScreenEvent.OnAttendeeAdded(attendee))
                        onEventScreenEvent(EventScreenEvent.OnShowVisitorDialog(false))

                    } ?: run {
                        _eventScreenState.update { eventScreenState ->
                            eventScreenState.copy(
                                isEmailVerified = false
                            )
                        }
                    }
                }
                is ResponseState.Failure -> {
                    _eventScreenState.update { eventScreenState ->
                        eventScreenState.copy(
                            isEmailVerified = false
                        )
                    }
                }
            }
        }
    }

    private fun fetchEventById(eventId: String) {
        viewModelScope.launch {
            eventRepository.getEventById(eventId).collectLatest { responseState ->
                when(responseState) {
                    ResponseState.Loading -> {
                        /** TODO Show loading */
                    }
                    is ResponseState.Success -> {
                        /* Update the state */
                        val event = responseState.data
                        _eventScreenState.update { eventScreenState ->
                            eventScreenState.copy(
                                eventId = event.id,
                                eventTitle = event.title,
                                eventDescription = event.description,
                                startDate = event.startDateTime.toZoneDateTime(),
                                endDate = event.endDateTime.toZoneDateTime(),
                                isUserEventCreator = event.isUserEventCreator
                            )
                        }
                    }
                    is ResponseState.Failure -> {
                        Log.d("EVENT_DETAIL", "${responseState.error.message}")
                    }
                }
            }
        }
    }

    private fun insertEventDetails(eventId: String) {
        val startDateTime = AlarmReminderProvider.getCombinedDateTime(eventScreenState.value.startTime, eventScreenState.value.startDate)
        val endDateTime = AlarmReminderProvider.getCombinedDateTime(eventScreenState.value.endTime, eventScreenState.value.endDate)
        val remindAt = AlarmReminderProvider.getRemindAt(eventScreenState.value.alarmReminderItem, startDateTime)

        val event = Event(
            id = eventId,
            title = eventScreenState.value.eventTitle,
            description = eventScreenState.value.eventDescription,
            startDateTime = startDateTime.toEpochSecond(),
            endDateTime = endDateTime.toEpochSecond(),
            remindAt = remindAt.toEpochSecond(),
            eventCreatorId = preferenceRepository.retrieveCurrentUserOrNull()?.userId ?: "",
            isUserEventCreator = true,
            isGoing = true,
            attendees = eventScreenState.value.attendees,
            photos = eventScreenState.value.listOfPhotoUri
        )

        viewModelScope.launch {
            when(val responseState = eventRepository.insertEvent(event)) {
                ResponseState.Loading -> {
                    /* TODO Show some loading progress */
                }
                is ResponseState.Success -> {
                    val alarmItem = event.toAlarmItem(AgendaType.EVENT)
                    alarmScheduler.scheduleAlarmReminder(alarmItem)
                    uploadEvent.upload(event, isEditMode = false)

                    _eventScreenState.update { eventScreenState ->
                        eventScreenState.copy(isSaved = true)
                    }
                }
                is ResponseState.Failure -> {
                    Log.e("EVENT_INSERT", "${responseState.error.message}")
                   /* TODO Show some kink of snack bar or toast message */
                }
            }
        }
    }

    private fun deleteEvent(eventId: String) {
        viewModelScope.launch {
            eventRepository.deleteEventById(eventId)
        }
    }
}