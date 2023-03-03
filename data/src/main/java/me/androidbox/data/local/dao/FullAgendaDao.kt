package me.androidbox.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import me.androidbox.data.local.DatabaseConstant
import me.androidbox.data.local.entity.FullAgendaEntity

@Dao
interface FullAgendaDao {

    @Query("SELECT * FROM ${DatabaseConstant.FULL_AGENDA}")
    fun getFullAgenda(): Flow<List<FullAgendaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFullAgenda()

    @Delete
    fun deleteAgenda(fullAgendaEntity: FullAgendaEntity)

    @Delete(FullAgendaEntity::class)
    fun deleteFullAgenda()
}