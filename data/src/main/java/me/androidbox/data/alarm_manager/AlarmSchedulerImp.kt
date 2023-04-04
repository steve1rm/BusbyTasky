package me.androidbox.data.alarm_manager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import dagger.hilt.android.qualifiers.ApplicationContext
import me.androidbox.domain.alarm_manager.AlarmItem
import me.androidbox.domain.alarm_manager.AlarmScheduler
import javax.inject.Inject

class AlarmSchedulerImp @Inject constructor(
    @ApplicationContext private val context: Context,
) : AlarmScheduler {

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_DESCRIPTION = "extra_description"
     }

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun scheduleAlarmReminder(alarmItem: AlarmItem) {
        /** Only set the alarm when the remindAt time is less than the current time */
        if (System.currentTimeMillis() < alarmItem.remindAt) {
            val alarmIntent = Intent(context, AlarmReceiver::class.java).apply {
                putExtra(EXTRA_ID, alarmItem.id)
                putExtra(EXTRA_TITLE, alarmItem.title)
                putExtra(EXTRA_DESCRIPTION, alarmItem.description)
            }

            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                alarmItem.remindAt,
                PendingIntent.getBroadcast(
                    context,
                    alarmItem.id.hashCode(),
                    alarmIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            )
        }
    }

    override fun cancelAlarmReminder(alarmItem: AlarmItem) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                alarmItem.id.hashCode(),
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        )
    }
}