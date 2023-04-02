package me.androidbox.data.alarm_manager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getShortExtra("EXTRA_MESSAGE", 0) ?: return
        println("Alarm triggered: $message")
    }
}