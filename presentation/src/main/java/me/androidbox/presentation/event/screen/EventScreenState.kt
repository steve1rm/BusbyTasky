package me.androidbox.presentation.event.screen

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import me.androidbox.component.agenda.AgendaActionType
import me.androidbox.component.agenda.VisitorType
import me.androidbox.component.event.VisitorInfo
import java.time.ZoneId
import java.time.ZonedDateTime

data class EventScreenState(
    val listOfPhotoUri: List<Uri> = mutableStateListOf<Uri>(),
    val selectedVisitorType: VisitorType = VisitorType.ALL,
    val selectedVisitor: VisitorInfo? = null,
    val eventTitle: String = "New Event",
    val eventDescription: String = "Description",
    val selectedAgendaActionType: AgendaActionType = AgendaActionType.DELETE_EVENT,
    val startTime: ZonedDateTime = ZonedDateTime.now(ZoneId.systemDefault()),
    val endTime: ZonedDateTime = ZonedDateTime.now(ZoneId.systemDefault()),
    val startDate: ZonedDateTime = ZonedDateTime.now(ZoneId.systemDefault()),
    val endDate: ZonedDateTime = ZonedDateTime.now(ZoneId.systemDefault()),
    val isStartDateTime: Boolean = false
)
