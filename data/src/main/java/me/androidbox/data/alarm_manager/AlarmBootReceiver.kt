package me.androidbox.data.alarm_manager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import me.androidbox.domain.alarm_manager.AlarmItem
import me.androidbox.domain.alarm_manager.AlarmScheduler
import me.androidbox.domain.authentication.remote.AgendaLocalRepository
import javax.inject.Inject

@AndroidEntryPoint
class AlarmBootReceiver : BroadcastReceiver() {

    @Inject
    lateinit var alarmScheduler: AlarmScheduler

    @Inject
    lateinit var agendaLocalRepository: AgendaLocalRepository

    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            CoroutineScope(Dispatchers.IO).launch {
                supervisorScope {
                    val eventsRemindAts = async {
                        agendaLocalRepository.fetchAllRemindAtFromEvents()
                    }

                    val taskRemindAts = async {
                        agendaLocalRepository.fetchAllRemindAtFromTasks()
                    }

                    val remindersRemindAt = async {
                        agendaLocalRepository.fetchAllRemindAtFromReminders()
                    }

                    val remindAts = eventsRemindAts.await() + taskRemindAts.await() + remindersRemindAt.await()

                    remindAts.forEach { agendaItem ->
                        alarmScheduler.scheduleAlarmReminder(
                            AlarmItem(
                                agendaId = agendaItem.id,
                                title = agendaItem.title,
                                description = agendaItem.description,
                                remindAt = agendaItem.remindAt,
                                agendaType = agendaItem.agendaType
                            )
                        )
                    }
                }
            }
        }
    }
}