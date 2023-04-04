package me.androidbox.presentation.event.screen

import androidx.annotation.StringRes
import androidx.compose.runtime.mutableStateListOf
import me.androidbox.component.agenda.AgendaActionType
import me.androidbox.component.agenda.VisitorType
import me.androidbox.component.event.VisitorInfo
import me.androidbox.component.R
import me.androidbox.component.agenda.AlarmReminderItem
import java.time.ZoneId
import java.time.ZonedDateTime

data class EventScreenState(
    val listOfPhotoUri: List<String> = mutableStateListOf<String>(),
    val selectedVisitorType: VisitorType = VisitorType.ALL,
    val selectedVisitor: VisitorInfo? = null,
    val eventTitle: String = "New Event",
    val eventDescription: String = "Description",
    val selectedAgendaActionType: AgendaActionType = AgendaActionType.DELETE_EVENT,
    val startTime: ZonedDateTime = ZonedDateTime.now(ZoneId.systemDefault()),
    val endTime: ZonedDateTime = ZonedDateTime.now(ZoneId.systemDefault()),
    val startDate: ZonedDateTime = ZonedDateTime.now(ZoneId.systemDefault()),
    val endDate: ZonedDateTime = ZonedDateTime.now(ZoneId.systemDefault()),
    val isStartDateTime: Boolean = false,
    val shouldOpenDropdown: Boolean = false,
    @StringRes val alarmReminderText: Int = R.string.ten_minutes_before,
    val alarmReminderItem: AlarmReminderItem = AlarmReminderItem.TEN_MINUTES
)
