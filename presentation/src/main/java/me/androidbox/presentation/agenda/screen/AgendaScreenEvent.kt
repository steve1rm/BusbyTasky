package me.androidbox.presentation.agenda.screen

import me.androidbox.domain.agenda.model.AgendaItem
import java.time.ZonedDateTime

sealed interface AgendaScreenEvent {
    data class OnDateChanged(val date: ZonedDateTime) : AgendaScreenEvent
    data class OnChangedShowDropdownStatus(val shouldOpen: Boolean) : AgendaScreenEvent
    data class OnChangeShowEditAgendaItemDropdownStatus(val shouldOpen: Boolean): AgendaScreenEvent
    data class OnAgendaItemClicked(val agendaItem: AgendaItem): AgendaScreenEvent
}