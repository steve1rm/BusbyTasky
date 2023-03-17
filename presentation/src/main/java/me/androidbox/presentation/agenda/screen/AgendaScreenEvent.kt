package me.androidbox.presentation.agenda.screen

sealed interface AgendaScreenEvent {
    data class OnDateChanged(val month: String) : AgendaScreenEvent
    object OnUserProfileClicked : AgendaScreenEvent
    object OnFloatingActionButtonClicked: AgendaScreenEvent
}