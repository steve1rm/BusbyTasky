package me.androidbox.presentation.event.screen

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

data class EventScreenState(
    val listOfPhotoUri: SnapshotStateList<Uri> = mutableStateListOf<Uri>()
)
