package me.androidbox.presentation.worker

import android.content.Context
import android.util.Log
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

@HiltWorker
class UploadEventWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParameters: WorkerParameters,
    private val eventService: EventService,
    private val eventConverter: EventConverter,
    private val createPhotoMultipart: CreatePhotoMultipart
) : CoroutineWorker(context, workerParameters)  {

    override suspend fun doWork(): Result {

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

        val eventRequestJson = eventConverter.toJson(eventRequest)

        /** TODO FIXME Serialize the event request to json
        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter<EventRequestDto>(EventRequestDto::class.java)
        val eventRequestJson = jsonAdapter.toJson(eventRequest)
         */

        val photoUrl = inputData.getString("photokey") ?: ""
        val listOfPhotos = listOf(photoUrl)

        /* Create the multipart for the list of photos */
        val listOfPhotoMultiPart = createPhotoMultipart.createMultipartPhotos(listOfPhotos)

        val responseResult = checkResult {
            eventService.createEvent(
                listOfPhoto = listOfPhotoMultiPart,
                eventBody = MultipartBody.Part.createFormData("create_event_request", eventRequestJson)
            )
        }

        /* Check if the request was successful or not */
        val result = responseResult.fold(
            onSuccess = {
                Result.success()
            },
            onFailure = {
                Log.e("WORK_MANAGER", it.localizedMessage)
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