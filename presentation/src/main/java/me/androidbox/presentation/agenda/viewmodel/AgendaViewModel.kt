package me.androidbox.presentation.agenda.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import me.androidbox.domain.agenda.usecase.UsersInitialsExtractionUseCase
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.authentication.preference.PreferenceRepository
import me.androidbox.domain.authentication.remote.AgendaLocalRepository
import me.androidbox.domain.authentication.remote.EventRepository
import me.androidbox.presentation.agenda.screen.AgendaScreenEvent
import me.androidbox.presentation.agenda.screen.AgendaScreenState
import java.time.ZoneId
import java.time.ZonedDateTime
import javax.inject.Inject

@HiltViewModel
class AgendaViewModel @Inject constructor(
    private val preferenceRepository: PreferenceRepository,
    private val usersInitialsExtractionUseCase: UsersInitialsExtractionUseCase,
    private val eventRepository: EventRepository,
    private val agendaLocalRepository: AgendaLocalRepository
) : ViewModel() {
    private var agendaJob: Job? = null

    private val _agendaScreenState = MutableStateFlow(AgendaScreenState())
    val agendaScreenState = _agendaScreenState.asStateFlow()

    init {
        getAuthenticatedUser()
        fetchAgendaItems(ZonedDateTime.now())
    }

    private fun getStartOffCurrentDay(agendaDate: ZonedDateTime = ZonedDateTime.now(ZoneId.systemDefault())): ZonedDateTime {
        return agendaDate
            .toLocalDate()
            .atStartOfDay(ZoneId.systemDefault())
    }

    private fun getEndOfCurrentDay(agendaDate: ZonedDateTime): ZonedDateTime {
        return agendaDate
            .plusDays(1L)
            .minusSeconds(1L)
    }

    fun fetchAgendaItems(agendaDate: ZonedDateTime = ZonedDateTime.now(ZoneId.systemDefault())) {
        agendaJob?.cancel()

        agendaJob = viewModelScope.launch {
            agendaLocalRepository.fetchAgenda(getStartOffCurrentDay(agendaDate).toEpochSecond(), getEndOfCurrentDay(agendaDate).toEpochSecond())
                .collectLatest { responseState ->
                    when(responseState) {
                        ResponseState.Loading -> {
                            /* TODO Show some form of loading */
                        }
                        is ResponseState.Failure -> {
                            /* TODO Show a toast or a snack bar message */
                            Log.e("AGENDA_VIEWMODEL", responseState.error.toString())
                        }

                        is ResponseState.Success -> {
                            /* TODO Update the state */
                            Log.d("AGENDA_VIEW", "${responseState.data}")
                            _agendaScreenState.update { agendaScreenState ->
                                agendaScreenState.copy(
                       //             agendaItems = responseState.data
                                )
                            }
                        }
                    }
                }
        }
    }

    fun onAgendaScreenEvent(agendaScreenEvent: AgendaScreenEvent) {
        when(agendaScreenEvent) {
            is AgendaScreenEvent.OnDateChanged -> {
                _agendaScreenState.update { agendaScreenState ->
                    agendaScreenState.copy(
                        selectedDate = agendaScreenEvent.date
                    )
                }
                fetchAgendaItems(agendaScreenEvent.date)
            }
            is AgendaScreenEvent.OnChangedShowDropdownStatus -> {
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