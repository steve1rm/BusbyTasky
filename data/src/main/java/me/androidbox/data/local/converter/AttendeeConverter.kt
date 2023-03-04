package me.androidbox.data.local.converter

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import me.androidbox.data.local.entity.AttendeeEntity

class AttendeeConverter {
    private val moshi = Moshi.Builder().build()
    private val attendeeListType = Types.newParameterizedType(List::class.java, AttendeeEntity::class.java)
    private val jsonAdapter = moshi.adapter<List<AttendeeEntity>>(attendeeListType)

    @TypeConverter
    fun fromJson(json: String): List<AttendeeEntity> {
        return jsonAdapter.fromJson(json) ?: emptyList()
    }

    @TypeConverter
    fun toJson(listOfAttendee: List<AttendeeEntity>): String {
        return jsonAdapter.toJson(listOfAttendee)
    }
}