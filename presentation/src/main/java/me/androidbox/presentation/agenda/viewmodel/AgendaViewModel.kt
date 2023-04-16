package me.androidbox.presentation.agenda.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import androidx.work.WorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import me.androidbox.domain.agenda.usecase.UsersInitialsExtractionUseCase
import me.androidbox.domain.authentication.ResponseState
import me.androidbox.domain.authentication.preference.PreferenceRepository
import me.androidbox.domain.authentication.remote.AgendaLocalRepository
import me.androidbox.domain.authentication.remote.EventRepository
import me.androidbox.domain.constant.SyncAgendaType
import me.androidbox.domain.work_manager.SyncAgendaItems
import me.androidbox.presentation.agenda.screen.AgendaScreenEvent
import me.androidbox.presentation.agenda.screen.AgendaScreenState
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AgendaViewModel @Inject constructor(
    private val preferenceRepository: PreferenceRepository,
    private val usersInitialsExtractionUseCase: UsersInitialsExtractionUseCase,
    private val eventRepository: EventRepository,
    private val agendaLocalRepository: AgendaLocalRepository,
    private val syncAgendaItems: SyncAgendaItems,
    private val workManager: WorkManager,
) : ViewModel() {
    private var agendaJob: Job? = null

    private val _agendaScreenState = MutableStateFlow(AgendaScreenState())
    val agendaScreenState = _agendaScreenState.asStateFlow()

    private lateinit var agendaSyncObserver: Observer<WorkInfo>
    private lateinit var workManagerLiveData: LiveData<WorkInfo>

    init {
        getAuthenticatedUser()
        fetchAgendaItems(ZonedDateTime.now())
        syncAgendaItems()
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
                                val agendaItems = responseState.data.events + responseState.data.tasks + responseState.data.reminders
                                agendaItems.sortedBy { agendaItem ->
                                    agendaItem.startDateTime
                                }
                                agendaScreenState.copy(
                                    agendaItems = agendaItems
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
        agendaSyncObserver = Observer { workInfo ->
            if(workInfo.state == WorkInfo.State.SUCCEEDED) {
                /* Delete the ids from the deleted sync table */
                viewModelScope.launch {
                    agendaLocalRepository.deleteEventSyncType(SyncAgendaType.DELETE)
                }
            }
        }

        val syncAgendaId = viewModelScope.async {
            syncAgendaItems.sync()
        }

        viewModelScope.launch {
            workManagerLiveData = workManager.getWorkInfoByIdLiveData(syncAgendaId.await())
            workManagerLiveData.observeForever(agendaSyncObserver)
        }
    }

    override fun onCleared() {
        workManagerLiveData.removeObserver(agendaSyncObserver)
        super.onCleared()
    }
}