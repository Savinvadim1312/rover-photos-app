package com.example.martian.rover_list.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "rovers")
data class RoverModel (

    @PrimaryKey
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName(value = "launch_date")
    val launchDate: String = "1/12/12",

    @field:SerializedName(value = "landing_date")
    val landingDate: String = "2/12/12",

    @field:SerializedName(value = "max_date")
    val maxDate: String = "3/12/12",

    @field:SerializedName(value = "status")
    val status: String = "complete",

    @field:SerializedName(value = "max_sol")
    val maxSol: Int = 0,

    @field:SerializedName(value = "total_photos")
    val totalPhotos: Int = 0
)