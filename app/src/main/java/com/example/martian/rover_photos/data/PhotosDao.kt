package com.example.martian.rover_photos.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * The Data Access Object for the PhotoModel class.
 */
@Dao
interface PhotosDao {

    @Query("SELECT * FROM photos WHERE rover_name = :roverName ORDER BY date DESC")
    fun getPhotos(roverName: String): LiveData<List<PhotoModel>>

    @Query("SELECT * FROM photos WHERE rover_name = :roverName ORDER BY date DESC")
    fun getPagedPhotosByRover(roverName: String): DataSource.Factory<Int, PhotoModel>

    @Query("SELECT * FROM photos ORDER BY date DESC")
    fun getPagedPhotos(): DataSource.Factory<Int, PhotoModel>

    @Query("SELECT * FROM photos WHERE id = :id")
    fun getPhoto(id: Int): LiveData<PhotoModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(photos: List<PhotoModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(photos: PhotoModel)
}
