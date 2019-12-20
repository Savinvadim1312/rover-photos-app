package com.example.martian.rover_photos.data

import com.beust.klaxon.Json
import com.google.gson.annotations.SerializedName

data class RoverCameraModel (
    val id: Int,
    val name: String,

    @field:SerializedName(value = "full_name")
    val fullName: String
//    val rover: RoverModel? = null
)