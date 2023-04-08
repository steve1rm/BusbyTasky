package me.androidbox.presentation.agenda.screen

import java.time.ZonedDateTime

sealed interface AgendaScreenEvent {
    data class OnDateChanged(val date: ZonedDateTime) : AgendaScreenEvent
    data class OnChangedShowDropdownStatus(val shouldOpen: Boolean) : AgendaScreenEvent
}