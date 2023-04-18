package me.androidbox.data.alarm_manager

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import me.androidbox.data.R
import me.androidbox.data.alarm_manager.AlarmSchedulerImp.Companion.EXTRA_AGENDA_TYPE
import me.androidbox.data.alarm_manager.AlarmSchedulerImp.Companion.EXTRA_DESCRIPTION
import me.androidbox.data.alarm_manager.AlarmSchedulerImp.Companion.EXTRA_ID
import me.androidbox.data.alarm_manager.AlarmSchedulerImp.Companion.EXTRA_TITLE
import me.androidbox.domain.alarm_manager.AgendaType
import me.androidbox.domain.constant.AgendaDeepLinks.EVENT_DEEPLINK

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent != null && context != null) {
            intent.extras?.let { bundle ->
                val agendaId = bundle.getString(EXTRA_ID) ?: ""
                val title = bundle.getString(EXTRA_TITLE) ?: "Title"
                val description = bundle.getString(EXTRA_DESCRIPTION) ?: "Description"
                val agendaType = bundle.getString(EXTRA_AGENDA_TYPE) ?: AgendaType.EVENT.name

                if (agendaId.isNotBlank()) {
                    createAndShowNotification(context, agendaId, title, description, agendaType)
                }
            }
        }
    }

    private fun createAndShowNotification(context: Context, agendaId: String, title: String, description: String, channelId: String) {
        val notificationManager = getNotificationManager(context)

        /** TODO Only Events for the moment, tasks and reminders coming soon... */
        val agendaIntent = Intent(
            Intent.ACTION_VIEW,
            "$EVENT_DEEPLINK".replace("{id}", agendaId).toUri())

        val pendingIntent = TaskStackBuilder.create(context).run {
            this.addNextIntentWithParentStack(agendaIntent)
            this.getPendingIntent(agendaId.hashCode(), PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(description)
            .setSmallIcon(R.drawable.bell)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(agendaId.hashCode(), notification)

        println("Alarm triggered: $agendaId $title $description $channelId")
    }

    private fun getNotificationManager(context: Context): NotificationManager {
        return context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
}