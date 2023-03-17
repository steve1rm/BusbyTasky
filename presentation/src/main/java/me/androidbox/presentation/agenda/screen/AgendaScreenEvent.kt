package me.androidbox.presentation.agenda.screen

sealed interface AgendaScreenEvent {
    data class OnDateChanged(val month: String): AgendaScreenEvent
    object OnDateClicked: AgendaScreenEvent
    object OnUserProfileClicked: AgendaScreenEvent
}