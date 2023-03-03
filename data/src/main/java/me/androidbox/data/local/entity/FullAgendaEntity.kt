package me.androidbox.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import me.androidbox.data.local.DatabaseConstant

@Entity(tableName = DatabaseConstant.FULL_AGENDA)
data class FullAgendaEntity(
    @Embedded val event: EventEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "eventId"
    )

    @Embedded val task: TaskEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "taskId"
    )

    @Embedded val reminder: ReminderEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "reminderId"
    )
    val listOfAgenda: List<AgendaEntity>
)
