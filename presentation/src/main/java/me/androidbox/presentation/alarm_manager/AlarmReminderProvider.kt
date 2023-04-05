package me.androidbox.presentation.alarm_manager

import me.androidbox.component.agenda.AlarmReminderItem
import java.time.ZoneId
import java.time.ZonedDateTime

object AlarmReminderProvider {

    fun getRemindAt(
        alarmReminderItem: AlarmReminderItem,
        startTime: ZonedDateTime,
        startDate: ZonedDateTime
    ): ZonedDateTime {
        /** Based on what alarm reminder the user has selected minus the time/date accordingly */
        return when (alarmReminderItem) {
            AlarmReminderItem.TEN_MINUTES -> {
                startTime.minusMinutes(10L)
            }
            AlarmReminderItem.THIRTY_MINUTES -> {
                startTime.minusMinutes(30L)
            }
            AlarmReminderItem.ONE_HOUR -> {
                startTime.minusHours(1L)
            }
            AlarmReminderItem.SIX_HOUR -> {
                startTime.minusHours(6L)
            }
            AlarmReminderItem.ONE_DAY -> {
                startDate.minusDays(1L)
            }
        }
    }

    fun getCombinedDateTime(time: ZonedDateTime, date: ZonedDateTime): ZonedDateTime {
        return ZonedDateTime.of(date.toLocalDate(), time.toLocalTime(), ZoneId.systemDefault())
    }
}


