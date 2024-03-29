package me.androidbox.presentation.agenda.screen

import java.time.ZonedDateTime
import me.androidbox.domain.agenda.model.AgendaItem

sealed interface AgendaScreenEvent {
    data class OnDateChanged(val date: ZonedDateTime) : AgendaScreenEvent
    data class OnSelectedDayChanged(val day: ZonedDateTime) : AgendaScreenEvent
    data class OnChangedShowDropdownStatus(val shouldOpen: Boolean) : AgendaScreenEvent
    data class OnChangeShowEditAgendaItemDropdownStatus(val shouldOpen: Boolean, val agendaItem: AgendaItem) : AgendaScreenEvent
    data class OnAgendaItemClicked(val agendaItem: AgendaItem) : AgendaScreenEvent
    data class OnOpenLogoutDropDownMenu(val shouldOpen: Boolean) : AgendaScreenEvent
    object OnLogoutClicked : AgendaScreenEvent
    data class OnSwipeToRefreshAgenda(val date: ZonedDateTime) : AgendaScreenEvent
}