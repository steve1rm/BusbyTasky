package me.androidbox.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import me.androidbox.data.local.DatabaseConstant
import me.androidbox.data.remote.model.AgendaModel

@Dao
interface AgendaDao {
    @Query("SELECT * FROM ${DatabaseConstant.AGENDA_TABLE}")
    fun getAgenda(): Flow<List<AgendaModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAgenda(agenda: AgendaModel)

    @Update
    fun updateAgenda(agenda: AgendaModel)

    @Delete
    fun deleteAgenda(agenda: AgendaModel)
}
