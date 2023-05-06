package me.androidbox.presentation.photo.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import me.androidbox.presentation.navigation.Screen.PhotoScreen.PHOTO_IMAGE_PATH
import me.androidbox.presentation.photo.screen.PhotoScreenEvent
import me.androidbox.presentation.photo.screen.PhotoScreenState
import javax.inject.Inject

@HiltViewModel
class PhotoScreenViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _photoScreenState = MutableStateFlow(PhotoScreenState())
    val photoScreenState = _photoScreenState.asStateFlow()

    init {
        savedStateHandle.get<String>(PHOTO_IMAGE_PATH)?.let { photoImage ->
            _photoScreenState.update { photoScreenState ->
                photoScreenState.copy(
                    photoSelected = photoImage
                )
            }
        }
    }

    fun onPhotoScreenEvent(photoScreenEvent: PhotoScreenEvent) {
        when(photoScreenEvent) {
            is PhotoScreenEvent.OnPhotoSelected -> {
                _photoScreenState.update { photoScreenState ->
                    photoScreenState.copy(
                        photoSelected = photoScreenEvent.photoImage
                    )
                }
            }

            is PhotoScreenEvent.OnPhotoDelete -> {
                _photoScreenState.update { photoScreenState ->
                    photoScreenState.copy(
                        photoSelected = photoScreenEvent.photoImage
                    )
                }
            }
        }
    }
}