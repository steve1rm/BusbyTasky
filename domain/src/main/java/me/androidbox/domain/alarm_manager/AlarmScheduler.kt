package me.androidbox.domain.alarm_manager

interface AlarmScheduler {
    fun scheduleAlarmReminder(alarmItem: AlarmItem)
    fun cancelAlarmReminder(alarmItem: AlarmItem)
}
