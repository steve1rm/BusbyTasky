package me.androidbox.presentation.event.screen

import android.net.Uri
import me.androidbox.component.agenda.AgendaActionType
import me.androidbox.component.agenda.VisitorType
import me.androidbox.component.event.VisitorInfo
import me.androidbox.domain.authentication.model.Event

sealed interface EventScreenEvent {
    data class OnPhotoUriAdded(val photoUri: Uri): EventScreenEvent
    /* Visitor Type i.e. All, Going, Not Going */
    data class OnSelectedVisitorType(val visitorType: VisitorType): EventScreenEvent
    data class OnSaveEditOrDescriptionContent(val content: String): EventScreenEvent
    data class OnDeleteVisitor(val visitorInfo: VisitorInfo): EventScreenEvent
    data class OnSelectedAgendaAction(val agendaActionType: AgendaActionType): EventScreenEvent
    object OnSaveEventDetails: EventScreenEvent
}