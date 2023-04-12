package me.androidbox.domain.repository

import me.androidbox.domain.agenda.model.Agenda
import java.time.ZoneId

interface AgendaRepository {
    suspend fun fetchAgendaForDay(zoneId: ZoneId, time: Long): Agenda
}
