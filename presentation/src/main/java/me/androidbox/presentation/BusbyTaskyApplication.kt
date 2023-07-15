package me.androidbox.presentation

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import me.androidbox.domain.alarm_manager.AgendaType

@HiltAndroidApp
class BusbyTaskyApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        createNotificationChannel(generateChannels())
    }

    private fun createNotificationChannel(listOfChannel: Map<String, String>) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            listOfChannel.map { mapOfChannel ->
                val notificationManager = getNotificationManager()
                val notificationChannel = NotificationChannel(
                    mapOfChannel.key,
                    mapOfChannel.value,
                    NotificationManager.IMPORTANCE_HIGH
                )

                notificationManager.createNotificationChannel(notificationChannel)
            }
        }
    }

    private fun getNotificationManager(): NotificationManager {
        return this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private fun generateChannels(): Map<String, String> {
        return mapOf(
            AgendaType.EVENT.name to "Notifications for Events",
            AgendaType.TASK.name to "Notifications for Tasks",
            AgendaType.REMINDER.name to "Notifications for Reminders"
        )
    }
}