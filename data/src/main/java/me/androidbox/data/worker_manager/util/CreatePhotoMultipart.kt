package me.androidbox.data.worker_manager.util

import okhttp3.MultipartBody

interface CreatePhotoMultipart {
    suspend fun createMultipartPhotos(listOfPhoto: List<String>): List<MultipartBody.Part>
}