package me.androidbox.data.remote.network.agenda

import me.androidbox.data.remote.model.response.AgendaDto
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.ZoneId

interface AgendaService {

    @GET("/agenda")
    suspend fun getAgendaForDay(
        @Query("timezone") zoneId: ZoneId,
        @Query("time") time: Long): AgendaDto
}
