package me.androidbox.presentation.event.screen

import android.net.Uri
import me.androidbox.component.agenda.VisitorType

sealed interface EventScreenEvent {
    data class OnPhotoUriAdded(val photoUri: Uri): EventScreenEvent

    /* Visitor Type i.e. All, Going, Not Going */
    data class OnSelectedVisitorType(val visitorType: VisitorType): EventScreenEvent

    data class OnDeleteVisitor(val visitorName: String): EventScreenEvent
}