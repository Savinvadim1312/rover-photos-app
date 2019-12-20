package com.example.martian.rover_photos.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.martian.rover_list.data.RoverModel
import com.google.gson.annotations.SerializedName

@Entity(tableName = "photos")
data class PhotoModel(

    @PrimaryKey
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("sol")
    val sol: Int,

    @field:SerializedName("earth_date")
    val date: String,

    @field:SerializedName("img_src")
    val imgSrc: String,


    @Embedded(prefix = "rover_")
    val rover: RoverModel?,

    @Embedded(prefix = "camera_")
    val camera: RoverCameraModel
)