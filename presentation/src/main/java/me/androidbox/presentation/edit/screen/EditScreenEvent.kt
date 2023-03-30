package me.androidbox.presentation.edit.screen

sealed interface EditScreenEvent {
    object OnSaveClicked : EditScreenEvent
    data class OnContentChanged(val content: String): EditScreenEvent
}