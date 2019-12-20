package com.example.martian.rover_photos.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.martian.data.resultLiveData
import com.example.martian.testing.OpenForTesting
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

@OpenForTesting
class PhotosRepository @Inject constructor(private val dao: PhotosDao,
                                           private val photosRemoteDataSource: PhotosRemoteDataSource){

    fun observePagedPhotos(connectivityAvailable: Boolean, roverName: String,
                         coroutineScope: CoroutineScope
    ) =
        if (connectivityAvailable) observeRemotePagedSets(roverName, coroutineScope)
        else observeLocalPagedSets(roverName)

    private fun observeLocalPagedSets(roverName: String): LiveData<PagedList<PhotoModel>> {
        val dataSourceFactory =
            dao.getPagedPhotosByRover(roverName)

        return LivePagedListBuilder(dataSourceFactory,
            PhotosPageDataSourceFactory.pagedListConfig()).build()
    }

    private fun observeRemotePagedSets(roverName: String, ioCoroutineScope: CoroutineScope)
            : LiveData<PagedList<PhotoModel>> {
        val dataSourceFactory = PhotosPageDataSourceFactory(roverName, photosRemoteDataSource,
            dao, ioCoroutineScope)
        return LivePagedListBuilder(dataSourceFactory,
            PhotosPageDataSourceFactory.pagedListConfig()).build()
    }

    fun observePhoto(id: Int) = dao.getPhoto(id)

    fun observePhotos(roverName: String) = resultLiveData(
        databaseQuery = { dao.getPhotos(roverName) },
        networkCall = { photosRemoteDataSource.fetchPhotos(roverName, 1) },
        saveCallResult = { dao.insertAll(it.photos) })

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: PhotosRepository? = null

        fun getInstance(dao: PhotosDao, photosRemoteDataSource: PhotosRemoteDataSource) =
            instance ?: synchronized(this) {
                instance
                    ?: PhotosRepository(dao, photosRemoteDataSource).also { instance = it }
            }
    }
}