package me.androidbox.data.worker_manager

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import me.androidbox.data.local.converter.EventConverter
import me.androidbox.domain.authentication.model.Event
import me.androidbox.domain.work_manager.UploadEvent
import javax.inject.Inject

class UploadEventImp @Inject constructor(
    private val workManager: WorkManager,
    private val eventConverter: EventConverter,
) : UploadEvent {

    companion object {
        const val IS_EDIT_MODE = "isEditMode"
        const val EVENT = "event"
        const val EVENT_PHOTOS = "eventPhotos"
    }

    override suspend fun upload(event: Event, isEditMode: Boolean) {
        println("event: ${event.id}")


        if(isEditMode) {
            /* Create update eventRequestDto*/
        }
        else {
            /* Create new request eventRequestDto*/
        }

        /* TODO Do this after the insert has completed */
        /*                  val photoUrl = eventScreenState.value.listOfPhotoUri
                          val inputData = workDataOf(
                              "photokey" to photoUrl
                          ) */

        /* Serialize the event to json */


        val uploadWorkerRequest  = OneTimeWorkRequestBuilder<UploadEventWorker>()
            .setConstraints(
                Constraints(Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build())
            ).build()

        workManager.enqueue(uploadWorkerRequest)
    }
}