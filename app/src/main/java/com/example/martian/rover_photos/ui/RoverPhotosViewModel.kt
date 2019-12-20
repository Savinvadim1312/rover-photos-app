package com.example.martian.rover_photos.ui

import androidx.lifecycle.ViewModel
import com.example.martian.di.CoroutineScropeIO
import com.example.martian.rover_photos.data.PhotosRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import javax.inject.Inject

class RoverPhotosViewModel @Inject constructor(private val repository: PhotosRepository,
    @CoroutineScropeIO private val ioCoroutineScope: CoroutineScope)
        : ViewModel() {

    var connectivityAvailable: Boolean = true
    var roverName: String = ""

    val photos by lazy {
        repository.observePagedPhotos(
            connectivityAvailable, roverName, ioCoroutineScope)
    }

    /**
     * Cancel all coroutines when the ViewModel is cleared.
     */
    override fun onCleared() {
        super.onCleared()
        ioCoroutineScope.cancel()
    }
}
