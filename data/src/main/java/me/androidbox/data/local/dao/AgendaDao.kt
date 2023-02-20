package me.androidbox.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import me.androidbox.data.local.DatabaseConstant
import me.androidbox.data.model.Agenda

@Dao
interface AgendaDao {
    @Query("SELECT * FROM ${DatabaseConstant.AGENDA_TABLE}")
    fun getAgenda(): Flow<List<Agenda>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAgenda(agenda: Agenda)

    @Update
    fun updateAgenda(agenda: Agenda)

    @Delete
    fun deleteAgenda(agenda: Agenda)
}
