package me.androidbox.data.local.converter

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import me.androidbox.data.local.entity.Attendee

class AttendeeConverter {
    private val moshi = Moshi.Builder().build()
    private val attendeeListType = Types.newParameterizedType(List::class.java, Attendee::class.java)
    private val jsonAdapter = moshi.adapter<List<Attendee>>(attendeeListType)

    @TypeConverter
    fun fromJson(json: String): List<Attendee> {
        return jsonAdapter.fromJson(json) ?: emptyList()
    }

    @TypeConverter
    fun toJson(listOfAttendee: List<Attendee>): String {
        return jsonAdapter.toJson(listOfAttendee)
    }
}