package me.androidbox.data.local.converter

import com.google.common.truth.Truth
import me.androidbox.data.remote.model.request.EventRequestDto
import org.junit.Test
import java.util.*
import kotlin.random.Random

class EventConverterTest {

    private val eventConverter = EventConverter()

    @Test
    fun `should serialize the event request into json string`() {
        // Arrange
        val eventRequestDto = generateEventRequestDto()

        // Act
        val actual = eventConverter.toJson(eventRequestDto)

        // Assert
        Truth.assertThat(actual).isNotEmpty()
    }

    @Test
    fun `should deserialize json string into a event result object`() {
        // Arrange
        val jsonString = "{\"id\":\"3061eb15-154b-4ea8-b89a-984cf16fa6d6\",\"title\":\"Title\",\"description\":\"description\",\"from\":3,\"to\":6,\"remindAt\":4,\"attendeeIds\":[\"25921c52-3e59-4acb-9c81-f79c8fbc2a27\",\"cd30b0a2-4015-43cb-b45d-70535bc31bb5\",\"8d1ed082-5859-4461-a1e6-268ff96d5ab9\",\"32ffd738-6a5f-4938-8f75-59302bd0443d\"]}"

        // Act
        val actual = eventConverter.fromJson(jsonString)

        // Assert
        Truth.assertThat(actual).isNotNull()
        Truth.assertThat(actual?.id).isEqualTo("3061eb15-154b-4ea8-b89a-984cf16fa6d6")
    }
}

private fun generateEventRequestDto(): EventRequestDto {
    return EventRequestDto(
        id = UUID.randomUUID().toString(),
        title = UUID.randomUUID().toString(),
        description = UUID.randomUUID().toString(),
        from = Random.nextLong(),
        to = Random.nextLong(),
        remindAt = Random.nextLong(),
        attendeeIds = listOf(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString()
        ))
}