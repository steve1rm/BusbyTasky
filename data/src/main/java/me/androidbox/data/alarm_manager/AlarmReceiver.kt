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

class AlarmReceiver : BroadcastReceiver() {
    companion object {
        const val CHANNEL_ID = "alarm_event_id"
        const val CHANNEL_NAME = "alarm_event_name"
        const val EXTRA_MESSAGE = "extra_message"
    }
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.getStringExtra(EXTRA_MESSAGE)?.let { message ->
            val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
                )

                notificationManager.createNotificationChannel(channel)
            }

            val notificationIntent = Intent(context, AlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getActivity(context, 1, notificationIntent, PendingIntent.FLAG_IMMUTABLE)
            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(context.getString(R.string.notification_title))
                .setSmallIcon(R.drawable.bell)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build()

            notificationManager.notify(1, notification)
            
            println("Alarm triggered: $message")
        }
    }
}