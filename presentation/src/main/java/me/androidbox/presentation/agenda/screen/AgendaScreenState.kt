package me.androidbox.presentation.agenda.screen

import me.androidbox.domain.agenda.model.AgendaItem
import java.time.ZoneId
import java.time.ZonedDateTime

data class AgendaScreenState(
    /* Calendar sets the first day to the agenda day selector */
    val selectedDate: ZonedDateTime = ZonedDateTime.now(ZoneId.systemDefault()),
    /* agenda day select fetches from the EP on the day that is selected */
    val selectedDay: ZonedDateTime = ZonedDateTime.now(ZoneId.systemDefault()),
    val usersInitials: String = "",
    val shouldOpenDropdown: Boolean = false,
    val shouldOpenEditAgendaDropdown: Boolean = false,
    val agendaItems: List<AgendaItem> = emptyList(),
    val agendaItemClicked: AgendaItem? = null,
    val shouldOpenLogoutDropDownMenu: Boolean = false,
    val deletedCacheCompleted: Boolean = false,
    val isRefreshingAgenda: Boolean = false,
)
