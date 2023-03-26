package me.androidbox.presentation.event.screen

import android.net.Uri
import androidx.compose.runtime.snapshots.SnapshotStateList

sealed interface EventScreenEvent {
    data class OnPhotoUriAdded(val listOfPhotoUri: SnapshotStateList<Uri>): EventScreenEvent
}