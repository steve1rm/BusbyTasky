package me.androidbox.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import me.androidbox.data.local.DatabaseConstant
import me.androidbox.data.local.entity.AgendaEntity

@Dao
interface AgendaDao {
    @Query("SELECT * FROM ${DatabaseConstant.AGENDA_TABLE}")
    fun getAgenda(): Flow<List<AgendaEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAgenda(agenda: AgendaEntity)

    @Update
    fun updateAgenda(agenda: AgendaEntity)

    @Delete
    fun deleteAgenda(agenda: AgendaEntity)
}
