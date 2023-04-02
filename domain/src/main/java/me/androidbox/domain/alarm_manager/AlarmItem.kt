package me.androidbox.domain.alarm_manager

import org.threeten.bp.ZonedDateTime

data class AlarmItem(
    val dateTime: ZonedDateTime,
    val message: String?
)
