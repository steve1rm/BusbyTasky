package me.androidbox.presentation.agenda.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.androidbox.domain.agenda.usecase.UsersInitialsExtractionUseCase
import me.androidbox.domain.authentication.preference.PreferenceRepository
import me.androidbox.domain.authentication.remote.EventRepository
import me.androidbox.presentation.agenda.screen.AgendaScreenEvent
import me.androidbox.presentation.agenda.screen.AgendaScreenState
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
class AgendaViewModel @Inject constructor(
    private val preferenceRepository: PreferenceRepository,
    private val usersInitialsExtractionUseCase: UsersInitialsExtractionUseCase,
    private val eventRepository: EventRepository
) : ViewModel() {

    private val _agendaScreenState = MutableStateFlow(AgendaScreenState())
    val agendaScreenState = _agendaScreenState.asStateFlow()

    init {
        getAuthenticatedUser()
        val listOfEvents = eventRepository.getEventsFromTimeStamp(ZonedDateTime.now().toEpochSecond(), ZonedDateTime.now().toEpochSecond())
        println("INIT BLOCK ")
    }

    fun onAgendaScreenEvent(agendaScreenEvent: AgendaScreenEvent) {
        when(agendaScreenEvent) {
            is AgendaScreenEvent.OnDateChanged -> {
                _agendaScreenState.update { agendaScreenState ->
                    agendaScreenState.copy(
                        displayMonth = agendaScreenEvent.date.month.toString()
                    )
                }
            }
            is AgendaScreenEvent.OnShowDropdown -> {
                _agendaScreenState.update { agendaScreenState ->
                    agendaScreenState.copy(
                        shouldOpenDropdown = agendaScreenEvent.shouldOpen
                    )
                }
            }
        }
    }

    private fun getAuthenticatedUser() {
        viewModelScope.launch {
            preferenceRepository.retrieveCurrentUserOrNull()?.let { authenticatedUser ->
                _agendaScreenState.update { agendaScreenState ->
                    agendaScreenState.copy(
                        usersInitials = usersInitialsExtractionUseCase.execute(authenticatedUser.fullName)
                    )
                }
            }
        }
    }
}