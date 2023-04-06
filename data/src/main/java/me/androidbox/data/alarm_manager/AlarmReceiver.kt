package me.androidbox.data.alarm_manager

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import me.androidbox.data.R
import me.androidbox.data.alarm_manager.AlarmSchedulerImp.Companion.EXTRA_DESCRIPTION
import me.androidbox.data.alarm_manager.AlarmSchedulerImp.Companion.EXTRA_ID
import me.androidbox.data.alarm_manager.AlarmSchedulerImp.Companion.EXTRA_TITLE

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent != null && context != null) {
            intent.extras?.let { bundle ->
                val agendaId = bundle.getString(EXTRA_ID) ?: ""
                val title = bundle.getString(EXTRA_TITLE) ?: "Title"
                val description = bundle.getString(EXTRA_DESCRIPTION) ?: "Description"

                if (agendaId.isNotBlank()) {
                    createAndShowNotification(context, agendaId, title, description)
                }
            }
        }
    }
    
    private fun createAndShowNotification(context: Context, agendaId: String, title: String, description: String) {
        val notificationManager = getNotificationManager(context)

        val notification = NotificationCompat.Builder(context, agendaId)
            .setContentTitle(title)
            .setContentText(description)
            .setSmallIcon(R.drawable.bell)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(agendaId.hashCode(), notification)

        println("Alarm triggered: $agendaId $title $description")
    }

    private fun getNotificationManager(context: Context): NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
}