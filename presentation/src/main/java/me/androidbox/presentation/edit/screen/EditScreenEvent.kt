package me.androidbox.presentation.edit.screen

sealed interface EditScreenEvent {
    data class OnSaveClicked(val content: String) : EditScreenEvent
    data class OnContentChanged(val content: String): EditScreenEvent
}