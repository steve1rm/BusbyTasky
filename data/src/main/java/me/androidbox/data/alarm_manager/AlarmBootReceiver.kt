package me.androidbox.data.alarm_manager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import me.androidbox.domain.alarm_manager.AlarmScheduler
import javax.inject.Inject

class AlarmBootReceiver : BroadcastReceiver() {

    @Inject
    lateinit var alarmScheduler: AlarmScheduler



    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent?.action == Intent.ACTION_BOOT_COMPLETED) {

        }
    }
}