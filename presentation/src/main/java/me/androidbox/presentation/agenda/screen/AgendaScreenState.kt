package me.androidbox.presentation.agenda.screen

import me.androidbox.domain.agenda.model.Event
import java.time.ZoneId
import java.time.ZonedDateTime

data class AgendaScreenState(
    val displayMonth: String = ZonedDateTime.now(ZoneId.systemDefault()).month.toString(),
    val usersInitials: String = "",
    val shouldOpenDropdown: Boolean = false,
    val eventDetails: List<Event> = emptyList()
)
