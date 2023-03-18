package me.androidbox.presentation.agenda.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import me.androidbox.presentation.agenda.screen.AgendaScreenEvent
import me.androidbox.presentation.agenda.screen.AgendaScreenState
import javax.inject.Inject

@HiltViewModel
class AgendaViewModel @Inject constructor() : ViewModel() {

    private val _agendaScreenState = MutableStateFlow(AgendaScreenState())
    val agendaScreenState = _agendaScreenState.asStateFlow()

    val calendarState by mutableStateOf(UseCaseState())


    fun onAgendaScreenEvent(agendaScreenEvent: AgendaScreenEvent) {
        when(agendaScreenEvent) {
            is AgendaScreenEvent.OnDateChanged -> {
                _agendaScreenState.update { agendaScreenState ->
                    agendaScreenState.copy(
                        displayMonth = agendaScreenEvent.month
                    )
                }
            }
            AgendaScreenEvent.OnUserProfileClicked -> {

            }
            AgendaScreenEvent.OnFloatingActionButtonClicked -> {

            }
        }
    }
}