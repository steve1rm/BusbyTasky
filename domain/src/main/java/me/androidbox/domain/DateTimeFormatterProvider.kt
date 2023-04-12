package me.androidbox.domain

import me.androidbox.domain.agenda.model.AgendaItem
import me.androidbox.domain.agenda.model.Event
import me.androidbox.domain.agenda.model.Reminder
import me.androidbox.domain.agenda.model.Task
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
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

    fun AgendaItem.toDisplayDateTime(): String {
        return when(this) {
            is Event -> {
                "${this.startDateTime.toZoneDateTime().formatDateTime(DATE_PATTERN)} - ${this.endDateTime.toZoneDateTime().formatDateTime(DATE_PATTERN)}"
            }
            is Task -> {
                "${this.startDateTime.toZoneDateTime().formatDateTime(DATE_PATTERN)}"
            }
            is Reminder -> {
                "${this.startDateTime.toZoneDateTime().formatDateTime(DATE_PATTERN)}"
            }
        }
    }
}