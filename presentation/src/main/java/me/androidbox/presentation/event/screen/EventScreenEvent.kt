package me.androidbox.presentation.event.screen

import android.net.Uri

sealed interface EventScreenEvent {
    data class OnPhotoUriAdded(val photoUri: Uri): EventScreenEvent
}