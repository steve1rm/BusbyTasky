package me.android.data.local.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import me.androidbox.data.local.database.BusbyTaskyDatabase
import me.androidbox.data.local.entity.TaskEntity
import org.junit.Before
import org.junit.Test
import java.util.UUID
import kotlin.random.Random

class TaskDaoTest {
    private lateinit var database: BusbyTaskyDatabase
    private val context: Context = ApplicationProvider.getApplicationContext()

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            context,
            BusbyTaskyDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @Test
    fun `insert_a_task_into_the_task_table`() {
        runBlocking {
            val task = TaskEntity(
                id = UUID.randomUUID().toString(),
                title = UUID.randomUUID().toString(),
                description = UUID.randomUUID().toString(),
                time = Random.nextLong(10000L),
                isDone = Random.nextBoolean(),
                remindAt = Random.nextLong(10000L)
            )

            database.taskDao().insertTask(task)

            val listOfTask = database.taskDao().getTasksFromTimeStamp(0, 100000L).first()

            assertThat(listOfTask.size).isEqualTo(1)
        }
    }
}