package me.androidbox.data.alarm_manager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.getStringExtra("EXTRA_MESSAGE")?.let { message ->
            /* TODO Add Notification here */
            println("Alarm triggered: $message")
        }
    }
}