package me.androidbox.data.alarm_manager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import me.androidbox.data.R
import me.androidbox.data.alarm_manager.AlarmSchedulerImp.Companion.EXTRA_AGENDA_TYPE
import me.androidbox.data.alarm_manager.AlarmSchedulerImp.Companion.EXTRA_DESCRIPTION
import me.androidbox.data.alarm_manager.AlarmSchedulerImp.Companion.EXTRA_ID
import me.androidbox.data.alarm_manager.AlarmSchedulerImp.Companion.EXTRA_TITLE

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        /* TODO Not sure if intent and context can be null but I guess is ok to cast it anyway */
        if(intent != null && context != null) {
            intent.extras?.let { bundle ->
                val agendaId = bundle.getString(EXTRA_ID) ?: ""
                val title = bundle.getString(EXTRA_TITLE) ?: "Title"
                val description = bundle.getString(EXTRA_DESCRIPTION) ?: "Description"
                val agendaType = bundle.getString(EXTRA_AGENDA_TYPE) ?: "Agenda"

                if (agendaId.isNotBlank()) {
                    createNotificationChannel(context, agendaId, agendaType)
                    createAndShowNotification(context, agendaId, title, description)
                }
            }
        }
    }

    private fun createNotificationChannel(context: Context, agendaId: String, agendaName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getNotificationManager(context)
            val notificationChannel = NotificationChannel(
                agendaId,
                agendaName,
                NotificationManager.IMPORTANCE_HIGH
            )

            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun createAndShowNotification(context: Context, agendaId: String, title: String, description: String) {
        val notificationManager = getNotificationManager(context)
        val notificationIntent = Intent(context, AlarmReceiver::class.java)

        /* TODO Add deeplinks to navigate to the detail agenda screen that the notification was trigger for */
        val pendingIntent = PendingIntent.getActivity(
            context,
            agendaId.hashCode(),
            notificationIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

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