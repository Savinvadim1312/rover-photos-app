package com.example.martian.rover_photos.data

import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.martian.data.Result;

class PhotoPageDataSource @Inject constructor(
    private val roverName: String,
    private val dataSource: PhotosRemoteDataSource,
    private val dao: PhotosDao,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, PhotoModel>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, PhotoModel>) {
        fetchData(1, params.requestedLoadSize) {
            callback.onResult(it, null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PhotoModel>) {
        val page = params.key
        fetchData(page, params.requestedLoadSize) {
            callback.onResult(it, page + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PhotoModel>) {
        val page = params.key
        fetchData(page, params.requestedLoadSize) {
            callback.onResult(it, page - 1)
        }
    }

    private fun fetchData(page: Int, pageSize: Int, callback: (List<PhotoModel>) -> Unit) {
        scope.launch(getJobErrorHandler()) {
            val response = dataSource.fetchPhotos(roverName, page)
            if (response.status == Result.Status.SUCCESS) {
                val results = response.data!!.photos
                dao.insertAll(results)
                callback(results)
            } else if (response.status == Result.Status.ERROR) {
                postError(response.message!!)
            }
        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        postError(e.message ?: e.toString())
    }

    private fun postError(message: String) {
        // TODO network error handling
        //networkState.postValue(NetworkState.FAILED)
    }

}