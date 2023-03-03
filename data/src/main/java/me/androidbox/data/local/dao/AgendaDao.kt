package me.androidbox.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import me.androidbox.data.local.DatabaseConstant
import me.androidbox.data.local.entity.AgendaEntity

@Dao
interface AgendaDao {
    @Query("SELECT * FROM ${DatabaseConstant.AGENDA_TABLE}")
    fun getAgenda(): Flow<AgendaEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAgenda(agenda: AgendaEntity)

    @Delete
    fun deleteAgenda(agenda: AgendaEntity)
}
