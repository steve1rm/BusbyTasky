package me.androidbox.domain

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object DateTimeFormatterProvider {

    const val DATE_PATTERN = "MMM, dd yyyy"
    const val TIME_PATTERN = "HH:mm a"

    fun ZonedDateTime.formatDateTime(pattern: String): String {
        return DateTimeFormatter.ofPattern(pattern, Locale.getDefault()).format(this)
    }
}