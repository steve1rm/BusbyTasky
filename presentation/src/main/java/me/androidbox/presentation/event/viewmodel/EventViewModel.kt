package me.androidbox.presentation.event.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.androidbox.domain.agenda.usecase.UsersInitialsExtractionUseCase
import me.androidbox.domain.alarm_manager.AgendaType
import me.androidbox.domain.alarm_manager.AlarmScheduler
import me.androidbox.domain.alarm_manager.toAlarmItem
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.authentication.model.Attendee
import me.androidbox.domain.authentication.model.Event
import me.androidbox.domain.authentication.preference.PreferenceRepository
import me.androidbox.domain.authentication.remote.EventRepository
import me.androidbox.domain.work_manager.UploadEvent
import me.androidbox.presentation.alarm_manager.AlarmReminderProvider
import me.androidbox.presentation.event.screen.EventScreenEvent
import me.androidbox.presentation.event.screen.EventScreenState
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val usersInitialsExtractionUseCase: UsersInitialsExtractionUseCase,
    private val preferenceRepository: PreferenceRepository,
    private val alarmScheduler: AlarmScheduler,
    private val uploadEvent: UploadEvent
) : ViewModel() {

    private val _eventScreenState: MutableStateFlow<EventScreenState> = MutableStateFlow(EventScreenState())
    val eventScreenState = _eventScreenState.asStateFlow()

    fun onEventScreenEvent(eventScreenEvent: EventScreenEvent) {
        when(eventScreenEvent) {
            is EventScreenEvent.OnPhotoUriAdded -> {
                _eventScreenState.update { eventScreenState ->
                    eventScreenState.copy(
                        listOfPhotoUri = eventScreenState.listOfPhotoUri + eventScreenEvent.photoUri
                    )
                }
            }
            is EventScreenEvent.OnSelectedVisitorType -> {
                _eventScreenState.update { eventScreenState ->
                    eventScreenState.copy(
                        selectedVisitorType = eventScreenEvent.visitorType
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
                _eventScreenState.update { eventScreenState ->
                    eventScreenState.copy(
                        selectedVisitor = eventScreenEvent.visitorInfo
                    )
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
                insertEventDetails()
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
                    eventScreenState.copy(
                        listOfAttendee = eventScreenState.listOfAttendee + eventScreenEvent.attendee
                    )
                }
            }
        }
    }

    private fun insertEventDetails() {
        val startDateTime = AlarmReminderProvider.getCombinedDateTime(eventScreenState.value.startTime, eventScreenState.value.startDate)
        val endDateTime = AlarmReminderProvider.getCombinedDateTime(eventScreenState.value.endTime, eventScreenState.value.endDate)
        val remindAt = AlarmReminderProvider.getRemindAt(eventScreenState.value.alarmReminderItem, startDateTime)

        val event = Event(
            id = UUID.randomUUID().toString(),
            title = eventScreenState.value.eventTitle,
            description = eventScreenState.value.eventDescription,
            startDateTime = startDateTime.toEpochSecond(),
            endDateTime = endDateTime.toEpochSecond(),
            remindAt = remindAt.toEpochSecond(),
            eventCreatorId = preferenceRepository.retrieveCurrentUserOrNull()?.userId ?: "",
            isUserEventCreator = false,
            isGoing = true,
            attendees = listOf( /** TODO Mock data until we have added real attendees */
                Attendee(1, "email", "job blogs", UUID.randomUUID().toString(), UUID.randomUUID().toString(), true, 4L),
                Attendee(2, "gmail", "peter rab", UUID.randomUUID().toString(), UUID.randomUUID().toString(), false, 2L)),
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
                }
                is ResponseState.Failure -> {
                    Log.e("EVENT_INSERT", "${responseState.error.message}")
                   /* TODO Show some kink of snack bar or toast message */
                }
            }
        }
    }
}