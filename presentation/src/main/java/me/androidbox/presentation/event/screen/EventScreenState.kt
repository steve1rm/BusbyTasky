package me.androidbox.presentation.event.screen

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import me.androidbox.component.agenda.AgendaActionType
import me.androidbox.component.agenda.VisitorType
import me.androidbox.component.event.VisitorInfo
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Locale

data class EventScreenState(
    val listOfPhotoUri: List<Uri> = mutableStateListOf<Uri>(),
    val selectedVisitorType: VisitorType = VisitorType.ALL,
    val selectedVisitor: VisitorInfo? = null,
    val eventTitle: String = "New Event",
    val eventDescription: String = "Description",
    val selectedAgendaActionType: AgendaActionType = AgendaActionType.DELETE_EVENT,
    val startTimeDuration: ZonedDateTime = ZonedDateTime.now(ZoneId.systemDefault()),
    val endTimeDuration: ZonedDateTime = ZonedDateTime.now(ZoneId.systemDefault()),
    val startDateDuration: ZonedDateTime = ZonedDateTime.now(ZoneId.systemDefault()),
    val endDateDuration: ZonedDateTime = ZonedDateTime.now(ZoneId.systemDefault())
)
