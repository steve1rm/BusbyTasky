package me.androidbox.presentation.agenda.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import me.androidbox.presentation.agenda.screen.AgendaScreenEvent
import me.androidbox.presentation.agenda.screen.AgendaScreenState
import javax.inject.Inject

@HiltViewModel
class AgendaViewModel @Inject constructor() : ViewModel() {

    private val agendaScreenMutableState = MutableStateFlow(AgendaScreenState())
    val agendaScreenState = agendaScreenMutableState.asStateFlow()

    fun onAgendaScreenEvent(agendaScreenEvent: AgendaScreenEvent) {
        when(agendaScreenEvent) {
            is AgendaScreenEvent.OnDateChanged -> {
                agendaScreenMutableState.value = agendaScreenState.value.copy(
                    displayMonth = agendaScreenEvent.month
                )
            }
            AgendaScreenEvent.OnUserProfileClicked -> {

            }
            AgendaScreenEvent.OnFloatingActionButtonClicked -> {

            }
        }
    }
}