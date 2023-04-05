package me.androidbox.data.mapper

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import me.androidbox.data.local.converter.AttendeeConverter
import me.androidbox.data.local.entity.EventEntity
import me.androidbox.domain.authentication.model.Event

fun List<String>.toJson(): String {
    val moshi = Moshi.Builder().build()
    val type = Types.newParameterizedType(List::class.java, String::class.java)
    val jsonAdapter = moshi.adapter<List<String>>(type)

    return jsonAdapter.toJson(this)
}

fun String.fromJson(): List<String> {
    val moshi = Moshi.Builder().build()
    val type = Types.newParameterizedType(List::class.java, String::class.java)
    val jsonAdapter = moshi.adapter<List<String>>(type)

    return jsonAdapter.fromJson(this) ?: emptyList()
}

fun EventEntity.toEvent(attendeeConverter: AttendeeConverter): Event {
    return Event(
        id = this.id,
        title = this.title,
        description = this.description,
        startDateTime = this.startDateTime,
        endDateTime = this.endDateTime,
        remindAt = this.remindAt,
        eventCreatorId = this.eventCreatorId,
        isUserEventCreator = this.isUserEventCreator,
        attendees = attendeeConverter.fromJson(this.attendees),
        photos = this.photos.fromJson()
    )
}

fun Event.toEventEntity(attendeeConverter: AttendeeConverter): EventEntity {
    return EventEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        startDateTime = this.startDateTime,
        endDateTime = this.endDateTime,
        remindAt = this.remindAt,
        eventCreatorId = this.eventCreatorId,
        isUserEventCreator = this.isUserEventCreator,
        attendees = attendeeConverter.toJson(this.attendees),
        photos = this.photos.toJson()
    )
}