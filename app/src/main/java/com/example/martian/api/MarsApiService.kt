package com.example.martian.api

import com.example.martian.rover_list.data.RoverModel
import com.example.martian.rover_photos.data.PhotoModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MarsApiService {

    companion object {
        const val ENDPOINT = "https://api.nasa.gov/mars-photos/api/v1/"
        const val API_KEY = "q29TiM228MPJxQHFigklIL63wCZfGe5Xvi3RvgQp"
    }

    @GET("rovers/")
    suspend fun getRovers(@Query("api_key") api_key: String? = API_KEY):
            Response<ResultsResponse<RoverModel>>

    @GET("rovers/{rover_name}/photos/")
    suspend fun getPhotos(@Path(value = "rover_name") rover_name: String,
                          @Query("page") page: Int? = null,
                          @Query("sol") sol: Int? = 1000,
                          @Query("api_key") api_key: String? = API_KEY): Response<ResultsResponse<PhotoModel>>

}