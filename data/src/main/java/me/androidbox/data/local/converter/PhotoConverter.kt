package me.androidbox.data.local.converter

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import me.androidbox.data.local.entity.Photo

class PhotoConverter {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val photoListType = Types.newParameterizedType(List::class.java, Photo::class.java)
    private val jsonAdapter = moshi.adapter<List<Photo>>(photoListType)

    @TypeConverter
    fun fromJson(json: String): List<Photo> {
        return jsonAdapter.fromJson(json) ?: emptyList()
    }

    @TypeConverter
    fun toJson(listOfPhoto: List<Photo>): String {
        return jsonAdapter.toJson(listOfPhoto)
    }
}