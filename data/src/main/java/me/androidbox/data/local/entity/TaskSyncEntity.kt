package me.androidbox.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import me.androidbox.data.local.DatabaseConstant
import me.androidbox.domain.constant.SyncAgendaType

@Entity(tableName = DatabaseConstant.TASK_SYNC_TABLE)
data class TaskSyncEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val syncAgendaType: SyncAgendaType
)
