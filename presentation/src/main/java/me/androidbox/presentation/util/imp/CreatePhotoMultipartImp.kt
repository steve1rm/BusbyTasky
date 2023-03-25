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

/*
content://com.android.providers.media.documents/document/image%3A38325
content://com.android.providers.media.documents/document/image%3A38324
content://com.google.android.apps.photos.contentprovider/0/1/mediakey%3A%2Flocal%253A44f502c3-7f36-4f9b-887c-5d8f2f45f3cc/ORIGINAL/NONE/image%2Fjpeg/430889281
content://com.google.android.apps.photos.contentprovider/-1/1/content%3A%2F%2Fmedia%2Fexternal%2Fimages%2Fmedia%2F37452/ORIGINAL/NONE/image%2Fjpeg/640889962
content://com.google.android.apps.photos.contentprovider/0/1/mediakey%3A%2Flocal%253A1e78ee30-5b74-4a1e-862b-a13525f26cd4/ORIGINAL/NONE/image%2Fjpeg/63330985
content://com.google.android.apps.photos.contentprovider/0/1/content%3A%2F%2Fmedia%2Fexternal%2Fimages%2Fmedia%2F38330/ORIGINAL/NONE/image%2Fjpeg/1631907737
content://com.android.providers.media.documents/document/image%3A38324
content://com.android.providers.media.documents/document/image%3A38337
* */

class CreatePhotoMultipartImp @Inject constructor(@ApplicationContext private val context: Context) : CreatePhotoMultipart {

    override suspend fun createMultipartPhotos(listOfPhoto: List<String>): List<MultipartBody.Part> {

        val listOfMultiPartBody = listOfPhoto.mapIndexed  { index, photo ->
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
        return withContext(Dispatchers.IO) {
           val inputStream = context.contentResolver.openInputStream(uri)

            val photoByteArray = inputStream?.use { inputStream ->
                inputStream.readBytes()
            } ?: byteArrayOf()

            photoByteArray
        }
    }
}