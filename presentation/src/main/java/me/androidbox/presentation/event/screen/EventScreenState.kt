package me.androidbox.presentation.event.screen

import android.net.Uri
import androidx.annotation.StringRes
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.res.stringResource
import me.androidbox.component.agenda.AgendaActionType
import me.androidbox.component.agenda.VisitorType
import me.androidbox.component.event.VisitorInfo
import me.androidbox.domain.alarm_manager.AlarmItem
import me.androidbox.presentation.R
import org.threeten.bp.ZonedDateTime

data class EventScreenState(
    val listOfPhotoUri: List<Uri> = mutableStateListOf<Uri>(),
    val selectedVisitorType: VisitorType = VisitorType.ALL,
    val selectedVisitor: VisitorInfo? = null,
    val eventTitle: String = "New Event",
    val eventDescription: String = "New Description",
    val selectedAgendaActionType: AgendaActionType = AgendaActionType.DELETE_EVENT,
    val shouldOpenDropdown: Boolean = false,
    val alarmItem: AlarmItem = AlarmItem(ZonedDateTime.now(), null),
    @StringRes val alarmReminderText: Int = me.androidbox.component.R.string.ten_minutes_before,
)
