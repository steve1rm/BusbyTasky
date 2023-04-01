package me.androidbox.presentation.edit.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import me.androidbox.presentation.edit.screen.EditScreenEvent
import me.androidbox.presentation.edit.screen.EditScreenState
import javax.inject.Inject

@HiltViewModel
class EditScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _editScreenState = MutableStateFlow(EditScreenState())
    val editScreenState = _editScreenState.asStateFlow()

    val contentSavedState = savedStateHandle.getStateFlow("content", "")

    fun onEditScreenEvent(editScreenEvent: EditScreenEvent) {
        when(editScreenEvent) {
            is EditScreenEvent.OnContentChanged -> {
                _editScreenState.update { editScreenState ->
                    editScreenState.copy(
                        content = editScreenEvent.content
                    )
                }
            }
            is EditScreenEvent.OnSaveClicked -> {
                Log.d("EDIT_SCREEN", "Content [ ${editScreenState.value} ]")
                /* TODO save the updated content to savedStateHandle to be retrieved in the navigation EventScreen when navigating back to there */
                savedStateHandle["content"] = editScreenEvent.content
            }
        }
    }
}