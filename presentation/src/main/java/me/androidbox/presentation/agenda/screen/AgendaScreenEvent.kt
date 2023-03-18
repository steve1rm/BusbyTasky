package me.androidbox.presentation.agenda.screen

import java.time.LocalDate

sealed interface AgendaScreenEvent {
    data class OnDateChanged(val date: LocalDate) : AgendaScreenEvent
}