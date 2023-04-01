package me.androidbox.presentation.event.screen

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import me.androidbox.component.agenda.AgendaActionType
import me.androidbox.component.agenda.VisitorType
import me.androidbox.component.event.VisitorInfo
import me.androidbox.domain.authentication.model.Event

data class EventScreenState(
    val listOfPhotoUri: List<Uri> = mutableStateListOf<Uri>(),
    val selectedVisitorType: VisitorType = VisitorType.ALL,
    val selectedVisitor: VisitorInfo? = null,
    val saveEditOrDescriptionContent: String = "",
    val selectedAgendaActionType: AgendaActionType = AgendaActionType.DELETE_EVENT,
)
