package me.androidbox.data.local.converter

import com.google.common.truth.Truth.assertThat
import me.androidbox.data.local.entity.Attendee
import org.junit.Test
import java.util.*
import kotlin.math.absoluteValue
import kotlin.random.Random

class AttendeeConverterTest {

    private val attendeeConverter = AttendeeConverter()

    @Test
    fun `should serialize the attendee list into json string`() {
        // Arrange
        val listOfAttendee = generateAttendeeList(3)

        // Act
        val actual = attendeeConverter.toJson(listOfAttendee)

        // Assert
        assertThat(actual).isNotEmpty()
    }

    @Test
    fun `should deserialize json string into a attendee list`() {
        // Arrange
        val jsonString = "[{\"id\":-1233288472,\"email\":\"c138aaa0-1457-43bd-9e5b-4839030cfbb4\",\"fullName\":\"58f30c1b-8571-420e-b6b4-cfbb58de577a\",\"userId\":\"681b12b8-7715-43da-a241-849e7de39903\",\"eventId\":\"19ef80f3-1feb-4c35-9aff-9bca0eb23c4f\",\"isGoing\":true,\"remindAt\":-664468997719518856}]"

        // Act
        val actual = attendeeConverter.fromJson(jsonString)

        // Assert
        assertThat(actual).hasSize(1)
        val attendee = actual.first()
        assertThat(attendee.id).isEqualTo(-1233288472)
        assertThat(attendee.email).isEqualTo("c138aaa0-1457-43bd-9e5b-4839030cfbb4")
    }

    @Test
    fun `should deserialize json string into a attendee list containing a size of 3`() {
        // Arrange
        val jsonString = "[{\"id\":946370399,\"email\":\"f56d3a5c-add2-4719-b4da-f125274e1de7\",\"fullName\":\"132de3b4-d00f-4061-81b7-a0f9bf7ea91a\",\"userId\":\"b168cfc1-fb62-4186-9609-60b7201481cf\",\"eventId\":\"9c58827c-49a0-404d-9715-754e0b7ed745\",\"isGoing\":false,\"remindAt\":6703432027504084949},{\"id\":1499174179,\"email\":\"174deeb9-e590-4db1-a5bf-7ac0d01730c7\",\"fullName\":\"5d48cac2-1dd8-4d85-9751-dd26f888a4b6\",\"userId\":\"62c7d91a-aef9-4d91-a62d-f749aab7c59e\",\"eventId\":\"f2a8b640-f82f-484d-90a7-c33605f28ed8\",\"isGoing\":true,\"remindAt\":2752546747229887038},{\"id\":167696774,\"email\":\"304bbd87-0848-46c3-a4ec-bdb81e9ba6a9\",\"fullName\":\"20046a9c-1021-4f03-8cb9-5b12083a9cc3\",\"userId\":\"ce4b6ae7-03aa-4144-ae96-e71abc5b6080\",\"eventId\":\"fc119fc9-92d7-4ed6-9943-058f1daa7eee\",\"isGoing\":false,\"remindAt\":4108255467512174613}]"

        // Act
        val actual = attendeeConverter.fromJson(jsonString)

        // Assert
        assertThat(actual).hasSize(3)
    }

    @Test
    fun `should find the correct attendee from the list of attendee`() {
        // Arrange
        val jsonString = "[{\"id\":946370399,\"email\":\"f56d3a5c-add2-4719-b4da-f125274e1de7\",\"fullName\":\"132de3b4-d00f-4061-81b7-a0f9bf7ea91a\",\"userId\":\"b168cfc1-fb62-4186-9609-60b7201481cf\",\"eventId\":\"9c58827c-49a0-404d-9715-754e0b7ed745\",\"isGoing\":false,\"remindAt\":6703432027504084949},{\"id\":1499174179,\"email\":\"174deeb9-e590-4db1-a5bf-7ac0d01730c7\",\"fullName\":\"5d48cac2-1dd8-4d85-9751-dd26f888a4b6\",\"userId\":\"62c7d91a-aef9-4d91-a62d-f749aab7c59e\",\"eventId\":\"f2a8b640-f82f-484d-90a7-c33605f28ed8\",\"isGoing\":true,\"remindAt\":2752546747229887038},{\"id\":167696774,\"email\":\"304bbd87-0848-46c3-a4ec-bdb81e9ba6a9\",\"fullName\":\"20046a9c-1021-4f03-8cb9-5b12083a9cc3\",\"userId\":\"ce4b6ae7-03aa-4144-ae96-e71abc5b6080\",\"eventId\":\"fc119fc9-92d7-4ed6-9943-058f1daa7eee\",\"isGoing\":false,\"remindAt\":4108255467512174613}]"

        // Act
        val actual = attendeeConverter.fromJson(jsonString)

        // Assert
        val actualAttendee = actual.firstOrNull { attendee ->
            attendee.userId.contentEquals("b168cfc1-fb62-4186-9609-60b7201481cf")
        }

        assertThat(actualAttendee?.userId).isEqualTo("b168cfc1-fb62-4186-9609-60b7201481cf")
    }

    @Test
    fun `should find the correct attendee from the list of attendee and remove it`() {
        // Arrange
        val jsonString = "[{\"id\":946370399,\"email\":\"f56d3a5c-add2-4719-b4da-f125274e1de7\",\"fullName\":\"132de3b4-d00f-4061-81b7-a0f9bf7ea91a\",\"userId\":\"b168cfc1-fb62-4186-9609-60b7201481cf\",\"eventId\":\"9c58827c-49a0-404d-9715-754e0b7ed745\",\"isGoing\":false,\"remindAt\":6703432027504084949},{\"id\":1499174179,\"email\":\"174deeb9-e590-4db1-a5bf-7ac0d01730c7\",\"fullName\":\"5d48cac2-1dd8-4d85-9751-dd26f888a4b6\",\"userId\":\"62c7d91a-aef9-4d91-a62d-f749aab7c59e\",\"eventId\":\"f2a8b640-f82f-484d-90a7-c33605f28ed8\",\"isGoing\":true,\"remindAt\":2752546747229887038},{\"id\":167696774,\"email\":\"304bbd87-0848-46c3-a4ec-bdb81e9ba6a9\",\"fullName\":\"20046a9c-1021-4f03-8cb9-5b12083a9cc3\",\"userId\":\"ce4b6ae7-03aa-4144-ae96-e71abc5b6080\",\"eventId\":\"fc119fc9-92d7-4ed6-9943-058f1daa7eee\",\"isGoing\":false,\"remindAt\":4108255467512174613}]"

        // Act
        val actual = attendeeConverter.fromJson(jsonString)

        // Assert
        val actualAttendee = actual.firstOrNull { attendee ->
            attendee.userId.contentEquals("b168cfc1-fb62-4186-9609-60b7201481cf")
        }

        val actualMutableList = actual.toMutableList()
        actualMutableList.remove(actualAttendee)

        assertThat(actualMutableList.toList()).hasSize(2)
    }
}

private fun generateAttendeeList(count: Int = 1): List<Attendee> {
    return generateSequence {
        generateAttendee()
    }
        .take(count)
        .toList()
}

private fun generateAttendee(): Attendee {
    return Attendee(
        id = Random.nextInt().absoluteValue,
        email = UUID.randomUUID().toString(),
        fullName = UUID.randomUUID().toString(),
        userId = UUID.randomUUID().toString(),
        eventId = UUID.randomUUID().toString(),
        isGoing = Random.nextBoolean(),
        remindAt = Random.nextLong().absoluteValue)
}