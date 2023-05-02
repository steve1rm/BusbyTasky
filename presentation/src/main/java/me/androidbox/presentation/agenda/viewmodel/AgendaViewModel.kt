package me.androidbox.presentation.agenda.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.androidbox.data.local.entity.EventSyncEntity
import me.androidbox.domain.agenda.usecase.UsersInitialsExtractionUseCase
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.authentication.preference.PreferenceRepository
import me.androidbox.domain.authentication.remote.AgendaLocalRepository
import me.androidbox.domain.authentication.remote.EventRepository
import me.androidbox.domain.authentication.usecase.LogoutUseCase
import me.androidbox.domain.constant.SyncAgendaType
import me.androidbox.domain.event.usecase.DeleteEventWithIdRemoteUseCase
import me.androidbox.domain.work_manager.AgendaSynchronizer
import me.androidbox.domain.work_manager.FullAgendaSynchronizer
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
    private val logoutUseCase: LogoutUseCase,
    private val deleteEventWithIdRemoteUseCase: DeleteEventWithIdRemoteUseCase,
    private val agendaLocalRepository: AgendaLocalRepository,
    private val agendaSynchronizer: AgendaSynchronizer,
    private val fullAgendaSynchronizer: FullAgendaSynchronizer
) : ViewModel() {
    private var agendaJob: Job? = null

    private val _agendaScreenState = MutableStateFlow(AgendaScreenState())
    val agendaScreenState = _agendaScreenState.asStateFlow()

    init {
        getAuthenticatedUser()
        fetchAgendaItems(ZonedDateTime.now())
        syncAgendaItems()
        syncFullAgendaItems()
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
                            _agendaScreenState.update { agendaScreenState ->
                                agendaScreenState.copy(
                                    isRefreshingAgenda = true
                                )
                            }
                        }
                        is ResponseState.Failure -> {
                            /* TODO Show a toast or a snack bar message */
                            Log.e("AGENDA_VIEWMODEL", responseState.error.toString())
                            _agendaScreenState.update { agendaScreenState ->
                                agendaScreenState.copy(
                                    isRefreshingAgenda = false
                                )
                            }
                        }
                        is ResponseState.Success -> {
                            /* TODO Update the state */
                            Log.d("AGENDA_VIEW", "${responseState.data}")
                            _agendaScreenState.update { agendaScreenState ->
                                val agendaItems = responseState.data.events + responseState.data.tasks + responseState.data.reminders
                                agendaItems.sortedBy { agendaItem ->
                                    agendaItem.startDateTime
                                }
                                agendaScreenState.copy(
                                    agendaItems = agendaItems,
                                    isRefreshingAgenda = false
                                )
                            }
                        }
                    }
                }
        }
    }

    fun deleteEventById(eventId: String) {
        viewModelScope.launch {
            eventRepository.deleteEventById(eventId)

            when(deleteEventWithIdRemoteUseCase.execute(eventId)) {
                ResponseState.Loading -> Unit /* TODO Show loading */
                is ResponseState.Success -> Unit /* Nothing to do here as the event from API was success */
                is ResponseState.Failure -> {
                    eventRepository.insertSyncEvent(eventId, SyncAgendaType.DELETE)
                }
            }
            fetchAgendaItems(agendaScreenState.value.selectedDate)
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
            is AgendaScreenEvent.OnChangeShowEditAgendaItemDropdownStatus -> {
                _agendaScreenState.update { agendaScreenState ->
                    agendaScreenState.copy(
                        shouldOpenEditAgendaDropdown = agendaScreenEvent.shouldOpen
                    )
                }
            }
            is AgendaScreenEvent.OnAgendaItemClicked -> {
                _agendaScreenState.update { agendaScreenState ->
                    agendaScreenState.copy(
                        agendaItemClicked = agendaScreenEvent.agendaItem
                    )
                }
            }

            AgendaScreenEvent.OnLogoutClicked -> {
                logoutCurrentUser()
            }

            is AgendaScreenEvent.OnOpenLogoutDropDownMenu -> {
                _agendaScreenState.update { agendaScreenState ->
                    agendaScreenState.copy(
                        shouldOpenLogoutDropDownMenu = agendaScreenEvent.shouldOpen
                    )
                }
            }

            is AgendaScreenEvent.OnSelectedDayChanged -> {
                _agendaScreenState.update { agendaScreenState ->
                    agendaScreenState.copy(
                        selectedDay = agendaScreenEvent.day
                    )
                }
            }

            is AgendaScreenEvent.OnSwipeToRefreshAgenda -> {
                fetchAgendaItems(agendaScreenEvent.date)
            }
        }
    }

    private fun logoutCurrentUser() {
        viewModelScope.launch {
            when(logoutUseCase.execute()) {
                ResponseState.Loading -> Unit /* TODO Show loading */

                is ResponseState.Success -> {
                    preferenceRepository.deleteCurrentUser()

                    val eventJob = viewModelScope.launch {
                        agendaLocalRepository.deleteAllEvents()
                    }
                    val taskJob = viewModelScope.launch {
                        agendaLocalRepository.deleteAllTasks()
                    }
                    val reminderJob = viewModelScope.launch {
                        agendaLocalRepository.deleteAllReminders()
                    }

                    listOf(eventJob, taskJob, reminderJob).forEach { job ->
                        job.join()
                    }

                    _agendaScreenState.update { agendaScreenState ->
                        agendaScreenState.copy(
                            deletedCacheCompleted = true
                        )
                    }
                }
                is ResponseState.Failure -> {
                    /** TODO show error message in snack bar */
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

    private fun syncAgendaItems() {
        agendaSynchronizer.sync()
    }

    private fun syncFullAgendaItems() {
        fullAgendaSynchronizer.sync()
    }
}