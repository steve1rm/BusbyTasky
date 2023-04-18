package me.androidbox.data.alarm_manager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import me.androidbox.data.local.agenda.AgendaLocalRepositoryImp
import me.androidbox.domain.alarm_manager.AlarmItem
import me.androidbox.domain.alarm_manager.AlarmScheduler
import javax.inject.Inject

class AlarmBootReceiver : BroadcastReceiver() {

    @Inject
    lateinit var alarmScheduler: AlarmScheduler

    @Inject
    lateinit var agendaLocalRepositoryImp: AgendaLocalRepositoryImp

    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            CoroutineScope(Dispatchers.IO).launch {
                supervisorScope {
                    val eventsRemindAts = async {
                        agendaLocalRepositoryImp.fetchAllRemindAtFromEvents()
                    }

                    val taskRemindAts = async {
                        agendaLocalRepositoryImp.fetchAllRemindAtFromTasks()
                    }

                    val remindersRemindAt = async {
                        agendaLocalRepositoryImp.fetchAllRemindAtFromReminders()
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
                    
                    coroutineContext.cancelChildren()
                }
            }
        }
    }
}