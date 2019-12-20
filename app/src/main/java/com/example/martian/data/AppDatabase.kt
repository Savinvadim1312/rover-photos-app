package com.example.martian.data

import android.content.Context
import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.martian.rover_list.data.RoverDao
import com.example.martian.rover_list.data.RoverModel
import com.example.martian.rover_photos.data.PhotoModel
import com.example.martian.rover_photos.data.PhotosDao

/**
 * The Room database for this app
 */
@Database(entities = [
        PhotoModel::class,
        RoverModel::class
    ],
    version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun photosDao(): PhotosDao

    abstract fun roversDao(): RoverDao


    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create  the database.
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "mars-photos-db")
                .build()
        }
    }
}
