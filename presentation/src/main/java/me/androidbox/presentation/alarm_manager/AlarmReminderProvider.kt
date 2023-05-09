package me.androidbox.presentation.alarm_manager

import me.androidbox.component.agenda.AlarmReminderItem
import java.time.ZoneId
import java.time.ZonedDateTime

object AlarmReminderProvider {

    fun getRemindAt(
        alarmReminderItem: AlarmReminderItem,
        startDateTime: ZonedDateTime,
    ): ZonedDateTime {
        /** Based on what alarm reminder the user has selected minus the time/date accordingly */
        return when (alarmReminderItem) {
            AlarmReminderItem.TEN_MINUTES -> {
                startDateTime.minusMinutes(10L)
            }
            AlarmReminderItem.THIRTY_MINUTES -> {
                startDateTime.minusMinutes(30L)
            }
            AlarmReminderItem.ONE_HOUR -> {
                startDateTime.minusHours(1L)
            }
            AlarmReminderItem.SIX_HOUR -> {
                startDateTime.minusHours(6L)
            }
            AlarmReminderItem.ONE_DAY -> {
                startDateTime.minusDays(1L)
            }
        }
    }

    fun getCombinedDateTime(time: ZonedDateTime, date: ZonedDateTime): ZonedDateTime {
        return ZonedDateTime.of(date.toLocalDate(), time.toLocalTime(), ZoneId.systemDefault())
    }

    fun getCombinedFromDate(date: ZonedDateTime): ZonedDateTime {
        return ZonedDateTime.of(date.toLocalDate(), date.toLocalTime(), ZoneId.systemDefault())
    }
}


