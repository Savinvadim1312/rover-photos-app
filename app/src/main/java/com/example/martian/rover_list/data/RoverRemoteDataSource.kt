package com.example.martian.rover_list.data

import com.example.martian.api.BaseDataSource
import com.example.martian.api.MarsApiService
import javax.inject.Inject

/**
 * Works with the Rover API to get data.
 */
class RoverRemoteDataSource @Inject constructor(private val service: MarsApiService) : BaseDataSource() {

    suspend fun fetchData() = getResult { service.getRovers() }

}