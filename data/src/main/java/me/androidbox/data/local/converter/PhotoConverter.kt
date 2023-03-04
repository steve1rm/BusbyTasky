package me.androidbox.data.local.converter

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import me.androidbox.data.local.entity.PhotoEntity

class PhotoConverter {
    private val moshi = Moshi.Builder().build()
    private val photoListType = Types.newParameterizedType(List::class.java, PhotoEntity::class.java)
    private val jsonAdapter = moshi.adapter<List<PhotoEntity>>(photoListType)

    @TypeConverter
    fun fromJson(json: String): List<PhotoEntity> {
        return jsonAdapter.fromJson(json) ?: emptyList()
    }

    @TypeConverter
    fun toJson(listOfPhoto: List<PhotoEntity>): String {
        return jsonAdapter.toJson(listOfPhoto)
    }
}