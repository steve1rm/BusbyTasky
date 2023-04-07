package me.androidbox.domain

import me.androidbox.domain.DateTimeFormatterProvider.toZoneDateTime
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor
import java.util.*

object DateTimeFormatterProvider {

    const val DATE_PATTERN = "MMM, dd yyyy"
    const val TIME_PATTERN = "HH:mm"

    fun ZonedDateTime.formatDateTime(pattern: String): String {
        return DateTimeFormatter.ofPattern(pattern, Locale.getDefault()).format(this)
    }

    fun Long.toZoneDateTime(): ZonedDateTime {
        val instant = Instant.ofEpochSecond(this)

        return ZonedDateTime.ofInstant(instant, ZoneId.systemDefault())
    }

    fun LocalDate.toZoneDateTime(): ZonedDateTime {
        return this.atStartOfDay(ZoneId.systemDefault())
    }
}