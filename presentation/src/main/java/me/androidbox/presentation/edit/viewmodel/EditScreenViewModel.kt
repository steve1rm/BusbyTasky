package me.androidbox.presentation.edit.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import me.androidbox.presentation.edit.screen.EditScreenEvent
import me.androidbox.presentation.edit.screen.EditScreenState
import javax.inject.Inject

@HiltViewModel
class EditScreenViewModel @Inject constructor() : ViewModel() {

    private val _editScreenState = MutableStateFlow(EditScreenState())
    val editScreenState = _editScreenState.asStateFlow()

    fun onEditScreenEvent(editScreenEvent: EditScreenEvent) {
        when(editScreenEvent) {
            is EditScreenEvent.OnContentChanged -> {
                _editScreenState.update { editScreenState ->
                    editScreenState.copy(
                        content = editScreenEvent.content
                    )
                }
            }
            EditScreenEvent.OnSaveClicked -> {
                Log.d("EDIT_SCREEN", "Content [ ${editScreenState.value} ]")
                /* TODO Not sure about this - Should this be saved directly to the DB which could be either the title of the description
                *   Or do we navigate back and save the title and description on the Edit Screen when the save is clicked */
            }
        }
    }
}