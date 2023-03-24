package me.androidbox.presentation.worker

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.squareup.moshi.Moshi
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import me.androidbox.data.R
import me.androidbox.data.local.converter.EventConverter
import me.androidbox.data.remote.model.request.EventRequestDto
import me.androidbox.data.remote.network.event.EventService
import me.androidbox.data.remote.util.CheckResult.checkResult
import me.androidbox.presentation.util.CreatePhotoMultipart
import okhttp3.MultipartBody
import java.util.*
import kotlin.random.Random

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

@HiltWorker
class UploadEventWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParameters: WorkerParameters,
    private val eventService: EventService,
    private val eventConverter: EventConverter,
    private val createPhotoMultipart: CreatePhotoMultipart
) : CoroutineWorker(context, workerParameters)  {

    override suspend fun doWork(): Result {
        startForegroundService()

        /** TODO Remove this mock data - this is just for testing purposes */
        val eventRequest = EventRequestDto(
            id = UUID.randomUUID().toString(),
            title = "Title",
            description = "description",
            from = 3L,
            to = 6L,
            remindAt = 4L,
            attendeeIds = listOf(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString()
            )
        )

        /* Serialize the event request to json */
        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter<EventRequestDto>(EventRequestDto::class.java)
        val eventRequestJson = jsonAdapter.toJson(eventRequest)

        /* These are just a random set of photos that I got from selecting photos from the device.
         * I just copied them here to try and send them as a multi-part request just to test if I can send them */
        val listOfPhotos = listOf(
            "content://com.google.android.apps.photos.contentprovider/0/1/mediakey%3A%2Flocal%253A44f502c3-7f36-4f9b-887c-5d8f2f45f3cc/ORIGINAL/NONE/image%2Fjpeg/430889281",
            "content://com.android.providers.media.documents/document/image%3A38324",
            "content://com.google.android.apps.photos.contentprovider/-1/1/content%3A%2F%2Fmedia%2Fexternal%2Fimages%2Fmedia%2F37452/ORIGINAL/NONE/image%2Fjpeg/640889962")

        /* Create the multipart for the list of photos */
        val listOfPhotoMultiPart = createPhotoMultipart.createMultipartPhotos(listOfPhotos)

        val responseResult = checkResult {
            eventService.createEvent(
                listOfPhoto = listOf<MultipartBody.Part>(), /** TODO Just sending an empty Part to just test the request */
                eventBody = MultipartBody.Part.createFormData("create_event_request", eventRequestJson)
            )
        }

        /* Check if the request was successful or not */
        val result = responseResult.fold(
            onSuccess = {
                Result.success()
            },
            onFailure = {
                Result.failure()
            }
        )

        return result
    }

    private suspend fun startForegroundService() {
        setForeground(
            ForegroundInfo(
                Random.nextInt(),
                NotificationCompat.Builder(context, "upload_event_channel")
                    .setSmallIcon(R.drawable.bell)
                    .setContentText("Uploading event...")
                    .setContentTitle("Uploading is in progress, please wait...")
                    .build()
            )
        )
    }
}