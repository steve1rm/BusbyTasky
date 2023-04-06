package me.androidbox.data.mapper.imp

import me.androidbox.data.local.entity.EventEntity
import me.androidbox.data.mapper.DataToDomainMapper
import me.androidbox.domain.authentication.model.Event
import javax.inject.Inject

class DataToDomainMapperImp @Inject constructor()
    : DataToDomainMapper<@JvmSuppressWildcards List<EventEntity>, @JvmSuppressWildcards List<Event>> {

    override fun invoke(listOfEventEntity: List<EventEntity>): List<Event> {
        return listOfEventEntity.map { entity ->
            Event(
                id = entity.id,
                title = entity.title,
                description = entity.description,
                startDateTime = entity.startDateTime,
                endDateTime = entity.endDateTime,
                remindAt = entity.remindAt,
                eventCreatorId = entity.eventCreatorId,
                isUserEventCreator = entity.isUserEventCreator,
                attendees = listOf(), // entity.attendees,
                photos = listOf(), // entity.photos,
                isGoing = true
            )
        }
    }
}
