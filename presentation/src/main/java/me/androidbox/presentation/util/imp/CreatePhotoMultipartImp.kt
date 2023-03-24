package me.androidbox.presentation.util.imp

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.androidbox.presentation.util.CreatePhotoMultipart
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.*
import javax.inject.Inject

class CreatePhotoMultipartImp @Inject constructor(@ApplicationContext private val context: Context) : CreatePhotoMultipart {

    override suspend fun createMultipartPhotos(listOfPhoto: List<String>): List<MultipartBody.Part> {
        val listOfPhotos = listOf("/photo1.jpg","/photo2.jpg")

        val listOfMultiPartBody = listOfPhotos.mapIndexed  { index, photo ->
            val url = Uri.parse(photo)
            val listOfPhotoByte = getByteArrayFromUri(url)
            val photoFileExtension = getFileExtensionFromUri(url)

            MultipartBody.Part.createFormData(
                name ="photo$index",
                filename = "${UUID.randomUUID()}$photoFileExtension",
                body = listOfPhotoByte.toRequestBody()
            )
        }

        return listOfMultiPartBody
    }

    private fun getFileExtensionFromUri(uri: Uri): String {
        val mimeTypeMap = MimeTypeMap.getSingleton()
        val extension = mimeTypeMap.getExtensionFromMimeType(context.contentResolver.getType(uri)) ?: ""

        return extension.lowercase(Locale.getDefault())
    }

    private suspend fun getByteArrayFromUri(uri: Uri): ByteArray {
       return withContext(Dispatchers.Default) {
            val inputStream = context.contentResolver.openInputStream(uri)

            val photoByteArray = inputStream?.use { inputStream ->
                inputStream.readBytes()
            } ?: byteArrayOf()

            photoByteArray
        }
    }
}