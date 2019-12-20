package com.example.martian.rover_photos.data

import com.example.martian.api.BaseDataSource
import com.example.martian.api.MarsApiService
import javax.inject.Inject

class PhotosRemoteDataSource @Inject constructor(private val service: MarsApiService) : BaseDataSource(){

    suspend fun fetchPhotos(roverName: String, page: Int)
            = getResult { service.getPhotos(roverName, page) }
}