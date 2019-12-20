package com.example.martian.rover_photos.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class PhotosPageDataSourceFactory @Inject constructor(
    private val roverName: String,
    private val dataSource: PhotosRemoteDataSource,
    private val dao: PhotosDao,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, PhotoModel>() {

    private val liveData = MutableLiveData<PhotoPageDataSource>()

    override fun create(): DataSource<Int, PhotoModel> {
        val source = PhotoPageDataSource(roverName, dataSource, dao, scope)
        liveData.postValue(source)
        return source
    }

    companion object {
        private const val PAGE_SIZE = 100

        fun pagedListConfig() = PagedList.Config.Builder()
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setPageSize(PAGE_SIZE)
            .setEnablePlaceholders(true)
            .build()
    }

}