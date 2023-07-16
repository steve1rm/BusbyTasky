package me.androidbox.data.remote.network.agenda

import java.time.ZoneId
import me.androidbox.data.remote.model.request.SyncAgendaDto
import me.androidbox.data.remote.model.response.AgendaDto
import me.androidbox.data.remote.model.response.FullAgendaDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AgendaService {

    @GET("/agenda")
    suspend fun getAgendaForDay(
        @Query("timezone") zoneId: ZoneId,
        @Query("time") time: Long): AgendaDto

    @POST("/syncAgenda")
    suspend fun syncAgenda(@Body syncAgenda: SyncAgendaDto)

    @GET("/fullAgenda")
    suspend fun fullAgenda(): FullAgendaDto
}
