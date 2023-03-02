package me.androidbox.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import me.androidbox.data.local.DatabaseConstant
import me.androidbox.data.local.entity.PhotoEntity

@Dao
interface PhotoDao {
    @Query("SELECT * FROM ${DatabaseConstant.PHOTO_TABLE}")
    fun getPhoto(): Flow<List<PhotoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhoto(photoEntity: PhotoEntity)

    @Delete
    fun deletePhoto(photoEntity: PhotoEntity)
}