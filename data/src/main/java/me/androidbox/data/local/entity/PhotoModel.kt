package me.androidbox.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import me.androidbox.data.local.DatabaseConstant

@Entity(tableName = DatabaseConstant.PHOTO_TABLE)
data class PhotoModel(
    /* TODO Can we use the key as a primary key as the key will be unique */
    @PrimaryKey(autoGenerate = false)
    val key: String,
    val url: String
)
