package me.androidbox.presentation.event.screen

import android.net.Uri
import androidx.annotation.StringRes
import me.androidbox.component.agenda.AgendaActionType
import me.androidbox.component.agenda.VisitorType
import me.androidbox.component.event.VisitorInfo
import java.time.ZonedDateTime
import me.androidbox.domain.alarm_manager.AlarmItem

sealed interface EventScreenEvent {
    data class OnPhotoUriAdded(val photoUri: String): EventScreenEvent
    /* Visitor Type i.e. All, Going, Not Going */
    data class OnSelectedVisitorType(val visitorType: VisitorType): EventScreenEvent
    data class OnSaveTitleOrDescription(val title: String, val description: String): EventScreenEvent
    data class OnDeleteVisitor(val visitorInfo: VisitorInfo): EventScreenEvent
    data class OnSelectedAgendaAction(val agendaActionType: AgendaActionType): EventScreenEvent
    object OnSaveEventDetails: EventScreenEvent
    data class OnStartTimeDuration(val startTime: ZonedDateTime): EventScreenEvent
    data class OnStartDateDuration(val startDate: ZonedDateTime): EventScreenEvent
    data class OnEndTimeDuration(val endTime: ZonedDateTime): EventScreenEvent
    data class OnEndDateDuration(val endDate: ZonedDateTime): EventScreenEvent
    data class OnStartDateTimeChanged(val isStartDateTime: Boolean): EventScreenEvent
    data class OnShowAlarmReminderDropdown(val shouldOpen: Boolean) : EventScreenEvent
    data class OnScheduleAlarmReminder(val alarmItem: AlarmItem) : EventScreenEvent
    data class OnAlarmReminderTextChanged(@StringRes val textId: Int): EventScreenEvent
}