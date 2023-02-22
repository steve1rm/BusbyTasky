package me.androidbox.data.local.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import me.androidbox.data.local.DatabaseConstant
import me.androidbox.data.remote.model.PhotoModel

@Dao
interface PhotoDao {
    @Query("SELECT * FROM ${DatabaseConstant.PHOTO_TABLE}")
    fun getPhoto(): Flow<List<PhotoModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPhoto(photoModel: PhotoModel)

    @Update
    fun updatePhoto(photoModel: PhotoModel)

    @Delete
    fun deletePhoto(photoModel: PhotoModel)
}