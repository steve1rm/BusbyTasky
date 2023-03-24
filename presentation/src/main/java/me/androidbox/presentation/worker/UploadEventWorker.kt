package me.androidbox.presentation.worker

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import me.androidbox.data.R
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
    private val createPhotoMultipart: CreatePhotoMultipart
) : CoroutineWorker(context, workerParameters)  {

    override suspend fun doWork(): Result {
        startForegroundService()

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
        val listOfPhotos = listOf("/photo1.jpg","/photo2.jpg")
        val listOfPhotoMultiPart = createPhotoMultipart.createMultipartPhotos(listOfPhotos)

        val responseResult = checkResult {
            eventService.createEvent(
                listOfPhoto = listOfPhotoMultiPart,
                eventBody = MultipartBody.Part.createFormData("create_event_request", "")
            )
        }

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