package me.androidbox.data.remote.agenda

import java.time.ZoneId
import javax.inject.Inject
import me.androidbox.data.mapper.toEvent
import me.androidbox.data.mapper.toReminder
import me.androidbox.data.mapper.toTask
import me.androidbox.data.remote.network.agenda.AgendaService
import me.androidbox.domain.agenda.model.Agenda
import me.androidbox.domain.repository.AgendaRemoteRepository

class AgendaRemoteRepositoryImp @Inject constructor(
    private val agendaService: AgendaService
) : AgendaRemoteRepository {

    override suspend fun fetchAgendaForDay(zoneId: ZoneId, time: Long): Agenda {
        val agendaDto = agendaService.getAgendaForDay(zoneId, time)

        val eventsDto = agendaDto.events
        val tasksDto = agendaDto.tasks
        val remindersDto = agendaDto.reminders

        val events = eventsDto.map { eventDto ->
            eventDto.toEvent()
        }

        val tasks = tasksDto.map { taskDto ->
            taskDto.toTask()
        }

        val reminders = remindersDto.map { reminderDto ->
            reminderDto.toReminder()
        }

        return Agenda(
            events = events,
            tasks = tasks,
            reminders = reminders
        )
    }
}