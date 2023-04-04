package me.androidbox.domain.alarm_manager

import java.time.ZonedDateTime

data class AlarmItem(
    val id: String,
    val title: String,
    val description: String,
    val remindAt: ZonedDateTime,
    val time: ZonedDateTime
)