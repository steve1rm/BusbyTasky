package me.androidbox.data.local.converter

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import me.androidbox.data.remote.model.request.EventRequestDto
import javax.inject.Inject

class EventConverter @Inject constructor(){
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val jsonAdapter = moshi.adapter(EventRequestDto::class.java)

    @TypeConverter
    fun fromJson(json: String): EventRequestDto? {
        return jsonAdapter.fromJson(json)
    }

    @TypeConverter
    fun toJson(eventRequestDto: EventRequestDto): String {
        return jsonAdapter.toJson(eventRequestDto)
    }
}