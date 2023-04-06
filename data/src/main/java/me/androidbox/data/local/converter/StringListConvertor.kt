package me.androidbox.data.local.converter

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class StringListConvertor {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    private val listType = Types.newParameterizedType(List::class.java, String::class.java)
    private val jsonAdapter = moshi.adapter<List<String>>(listType)

    @TypeConverter
    fun fromJson(json: String): List<String> {
        return jsonAdapter.fromJson(json) ?: emptyList()
    }

    @TypeConverter
    fun toJson(list: List<String>): String {
        return jsonAdapter.toJson(list)
    }
}