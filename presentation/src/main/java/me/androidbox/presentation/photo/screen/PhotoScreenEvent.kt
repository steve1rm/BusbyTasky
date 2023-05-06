package me.androidbox.presentation.photo.screen

sealed interface PhotoScreenEvent {
    data class OnPhotoSelected(val photoImage: String) : PhotoScreenEvent
    data class OnPhotoDelete(val photoImage: String) : PhotoScreenEvent
}