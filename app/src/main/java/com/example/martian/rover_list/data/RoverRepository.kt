package com.example.martian.rover_list.data

import com.example.martian.data.resultLiveData
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository module for handling data operations.
 */
@Singleton
class RoverRepository @Inject constructor(private val dao: RoverDao,
                                              private val remoteSource: RoverRemoteDataSource) {

    val rovers = resultLiveData(
        databaseQuery = { dao.getRovers() },
        networkCall = { remoteSource.fetchData() },
        saveCallResult = { dao.insertAll(it.rovers) })

}
