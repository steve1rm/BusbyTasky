package me.androidbox.presentation.event.screen

import androidx.compose.runtime.mutableStateListOf
import me.androidbox.component.agenda.AgendaActionType
import me.androidbox.component.agenda.AlarmReminderItem
import me.androidbox.component.agenda.VisitorFilterType
import me.androidbox.component.event.VisitorInfo
import me.androidbox.domain.agenda.model.Attendee
import java.time.ZoneId
import java.time.ZonedDateTime

data class EventScreenState(
    val listOfPhotoUri: List<String> = mutableStateListOf<String>(),
    val selectedVisitor: VisitorInfo? = null,
    val visitorEmail: String = "",
    val shouldShowVisitorDialog: Boolean = false,
    val selectedVisitorFilterType: VisitorFilterType = VisitorFilterType.ALL,
    val visitors: List<VisitorInfo> = mutableStateListOf(),
    val isEmailVerified: Boolean = true,
    val eventTitle: String = "New Event",
    val eventDescription: String = "Description",
    val eventId: String = "",
    val selectedAgendaActionType: AgendaActionType = AgendaActionType.DELETE_EVENT,
    val startTime: ZonedDateTime = ZonedDateTime.now(ZoneId.systemDefault()),
    val endTime: ZonedDateTime = ZonedDateTime.now(ZoneId.systemDefault()),
    val startDate: ZonedDateTime = ZonedDateTime.now(ZoneId.systemDefault()),
    val endDate: ZonedDateTime = ZonedDateTime.now(ZoneId.systemDefault()),
    val isStartDateTime: Boolean = false,
    val shouldOpenDropdown: Boolean = false,
    val alarmReminderItem: AlarmReminderItem = AlarmReminderItem.TEN_MINUTES,
    val attendees: List<Attendee> = listOf(),
    val isSaved: Boolean = false,
    val isEditMode: Boolean = false,
    val isUserEventCreator: Boolean = true,
    val shouldShowDeleteAlertDialog: Boolean = false
)
