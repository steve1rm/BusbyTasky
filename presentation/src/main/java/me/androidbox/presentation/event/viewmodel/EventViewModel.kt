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
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.authentication.model.Event
import me.androidbox.domain.authentication.remote.EventRepository
import me.androidbox.presentation.event.screen.EventScreenEvent
import me.androidbox.presentation.event.screen.EventScreenState
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val usersInitialsExtractionUseCase: UsersInitialsExtractionUseCase
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
            is EventScreenEvent.OnSaveEditOrDescriptionContent -> {
                _eventScreenState.update { eventScreenState ->
                    eventScreenState.copy(
                        saveEditOrDescriptionContent = eventScreenEvent.content
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
                /* TODO Get all the information related from teh EventScreenState
                *       and create the Event object */

                /* Then insert this event into the room db
                val Event = Event(
                    id = UUID.randomUUID().toString(),
                    description = eventScreenState.value.saveEditOrDescriptionContent,
                    photos = eventScreenState.value.listOfPhotoUri,
                    isUserEventCreator = true,
                    ...
                )
                   insertEventDetails(event)
                 */
            }
        }
    }

    private fun insertEventDetails(event: Event) {
        viewModelScope.launch {
            eventRepository.insertEvent(event)
        }
    }

    /* TODO Remove this as I am only using this to MOCK data to test inserting and fetching */
    fun insertEvent() {
        val event1 = Event(
            id = UUID.randomUUID().toString(),
            title = "event 1",
            description = "description 1",
            startDateTime = 1L,
            endDateTime = 3L,
            remindAt = 3L,
            eventCreatorId = "host 1",
            isUserEventCreator = false,
            attendees = "attendee 1",
            photos = "photos 1"
        )

        val event2 = Event(
            id = UUID.randomUUID().toString(),
            title = "event 1",
            description = "description 2",
            startDateTime = 4L,
            endDateTime = 6L,
            remindAt = 3L,
            eventCreatorId = "host 1",
            isUserEventCreator = false,
            attendees = "attendee 2",
            photos = "photos 2"
        )

        val event3 = Event(
            id = UUID.randomUUID().toString(),
            title = "event 3",
            description = "description 3",
            startDateTime = 7L,
            endDateTime = 9L,
            remindAt = 3L,
            eventCreatorId = "host 3",
            isUserEventCreator = false,
            attendees = "attendee 3",
            photos = "photos 3"
        )

        viewModelScope.launch {
            eventRepository.insertEvent(event1)
            eventRepository.insertEvent(event2)
            eventRepository.insertEvent(event3)

            eventRepository.getEventsFromTimeStamp(4L, 6L).collect { responseState ->
                when(responseState) {
                    is ResponseState.Success -> {
                        responseState.data.map { event ->
                            Log.d("EVENT", "[ ${event.id} ] [ ${event.startDateTime} | ${event.endDateTime} ]")
                        }
                    }
                    is ResponseState.Failure -> {
                        Log.e("EVENT", "ERROR ${responseState.error}")
                    }
                    ResponseState.Loading -> {

                    }
                }
            }
        }
    }
}