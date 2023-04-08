package me.androidbox.presentation.agenda.screen

import me.androidbox.domain.agenda.model.AgendaItem
import java.time.ZoneId
import java.time.ZonedDateTime

data class AgendaScreenState(
    val displayMonth: String = ZonedDateTime.now(ZoneId.systemDefault()).month.toString(),
    val usersInitials: String = "",
    val shouldOpenDropdown: Boolean = false,
    val agendaItems: List<AgendaItem> = emptyList()
)
