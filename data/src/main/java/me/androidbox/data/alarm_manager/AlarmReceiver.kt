package me.androidbox.data.alarm_manager

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
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


        val route = EVENT_DEEPLINK.replace("{id}", agendaId).toUri()

        // EVENT_DEEPLINK.replace("{id}", agendaId).toUri())

        /** TODO Only Events for the moment, tasks and reminders coming soon... */
        val agendaIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("busbyapp://androidbox.me/event_screen/09db6048-2fa3-459a-8d0d-fcbb7f40ca86"))

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

// https://androidbox.me/event?id=23334853-7f78-4f5a-8286-0329e5478ff6

// busbyTasky://event?id=e29fab54-d42e-429f-8f92-8ec97fd8007a