package me.androidbox.presentation.event.screen

import android.net.Uri
import me.androidbox.component.agenda.VisitorType

sealed interface EventScreenEvent {
    data class OnPhotoUriAdded(val photoUri: Uri): EventScreenEvent
    data class OnSelectedVisitorType(val visitorType: VisitorType): EventScreenEvent
}