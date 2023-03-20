package me.androidbox.presentation.event.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.authentication.remote.EventRepository
import java.util.UUID
import javax.inject.Inject
import me.androidbox.domain.authentication.model.Event

@HiltViewModel
class EventViewModel @Inject constructor(
    private val eventRepository: EventRepository
) : ViewModel() {

    fun insertEvent() {
        val event1 = Event(
            id = UUID.randomUUID().toString(),
            title = "event 1",
            description = "description 1",
            startDateTime = 1L,
            endDateTime = 3L,
            remindAt = 3L,
            host = "host 1",
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
            host = "host 1",
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
            host = "host 3",
            isUserEventCreator = false,
            attendees = "attendee 3",
            photos = "photos 3"
        )

        viewModelScope.launch {
            eventRepository.insertEvent(event1)
            eventRepository.insertEvent(event2)
            eventRepository.insertEvent(event3)

            //  eventRepository.deleteEventById(event2.id)

                val responseState = eventRepository.getEventsFromTimeStamp(1L, 3L)
                    when (responseState) {
                        is ResponseState.Success -> {
                            val count = responseState.data.count()
                        }
                        is ResponseState.Failure -> {
                            println(responseState.error.message)
                        }
                        else -> Unit
                    }
                }

        println("")
    }
}