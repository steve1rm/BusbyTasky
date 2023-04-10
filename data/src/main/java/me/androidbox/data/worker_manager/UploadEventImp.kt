package me.androidbox.data.worker_manager

import androidx.work.*
import com.squareup.moshi.Moshi
import me.androidbox.data.mapper.toCreateEventDto
import me.androidbox.data.mapper.toUpdateEventDto
import me.androidbox.data.remote.model.request.EventCreateRequestDto
import me.androidbox.data.remote.model.request.EventUpdateRequestDto
import me.androidbox.domain.authentication.model.Event
import me.androidbox.domain.work_manager.UploadEvent
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class UploadEventImp @Inject constructor(
    private val workManager: WorkManager,
    private val moshi: Moshi
) : UploadEvent {

    companion object {
        const val IS_EDIT_MODE = "isEditMode"
        const val EVENT = "event"
        const val EVENT_PHOTOS = "eventPhotos"
        const val ERROR = "error"
    }

    override suspend fun upload(event: Event, isEditMode: Boolean) {
        /** Serialize the event to json */
        val eventRequestJson = if(isEditMode) {
            /** request for update eventRequestDto */
            val jsonAdapter = moshi.adapter(EventUpdateRequestDto::class.java)
            jsonAdapter.toJson(event.toUpdateEventDto())
        }
        else {
            /** request for Create eventRequestDto */
            val jsonAdapter = moshi.adapter(EventCreateRequestDto::class.java)
            jsonAdapter.toJson(event.toCreateEventDto())
        }

        /** Create input data to send to the work that will be retrieved */
        val eventInputData = workDataOf(
            IS_EDIT_MODE to isEditMode,
            EVENT to eventRequestJson,
            EVENT_PHOTOS to event.photos.toTypedArray())

        val uploadWorkerRequest  = OneTimeWorkRequestBuilder<UploadEventWorker>()
            .setConstraints(Constraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()))
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                WorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.SECONDS)
            .setInputData(inputData = eventInputData).build()

        workManager.enqueue(uploadWorkerRequest)
    }
}