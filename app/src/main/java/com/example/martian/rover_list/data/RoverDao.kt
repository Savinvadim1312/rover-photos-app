package com.example.martian.rover_list.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * The Data Access Object for the Rover class.
 */
@Dao
interface RoverDao {

    @Query("SELECT * FROM rovers ORDER BY id DESC")
    fun getRovers(): LiveData<List<RoverModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(plants: List<RoverModel>)
}
