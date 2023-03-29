package me.androidbox.presentation.event.screen

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import me.androidbox.component.agenda.VisitorType

data class EventScreenState(
    val listOfPhotoUri: List<Uri> = mutableStateListOf<Uri>(),
    val selectedVisitorType: VisitorType = VisitorType.ALL
)
