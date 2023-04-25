package me.androidbox.presentation.agenda.screen

import me.androidbox.domain.agenda.model.AgendaItem
import java.time.ZoneId
import java.time.ZonedDateTime

data class AgendaScreenState(
    val selectedDate: ZonedDateTime = ZonedDateTime.now(ZoneId.systemDefault()),
    val usersInitials: String = "",
    val shouldOpenDropdown: Boolean = false,
    val shouldOpenEditAgendaDropdown: Boolean = false,
    val agendaItems: List<AgendaItem> = emptyList(),
    val agendaItemClicked: AgendaItem? = null,
    val shouldOpenLogoutDropDownMenu: Boolean = false
)
