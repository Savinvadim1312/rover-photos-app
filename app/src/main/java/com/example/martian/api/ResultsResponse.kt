package com.example.martian.api

import com.google.gson.annotations.SerializedName

data class ResultsResponse<T>(
    @SerializedName("photos")
    val photos: List<T>,

    @SerializedName("rovers")
    val rovers: List<T>
)