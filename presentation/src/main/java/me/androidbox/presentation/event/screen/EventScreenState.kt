package me.androidbox.presentation.event.screen

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf

data class EventScreenState(
    val listOfPhotoUri: List<Uri> = mutableStateListOf<Uri>()
)
