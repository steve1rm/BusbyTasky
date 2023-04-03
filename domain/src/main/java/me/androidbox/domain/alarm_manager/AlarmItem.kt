package me.androidbox.domain.alarm_manager

import java.time.ZonedDateTime


data class AlarmItem(
    val dateTime: ZonedDateTime,
    val message: String?
) {
    val long = dateTime.toEpochSecond() * 100


}
