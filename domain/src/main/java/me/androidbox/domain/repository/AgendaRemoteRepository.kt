package me.androidbox.domain.repository

import java.time.ZoneId
import me.androidbox.domain.agenda.model.Agenda

interface AgendaRemoteRepository {
    suspend fun fetchAgendaForDay(zoneId: ZoneId, time: Long): Agenda
}
