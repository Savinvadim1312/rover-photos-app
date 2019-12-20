package com.example.martian.photo

import androidx.lifecycle.ViewModel
import com.example.martian.rover_photos.data.PhotosRepository
import javax.inject.Inject

/**
 * The ViewModel used in [PhotoFragment].
 */
class PhotoViewModel @Inject constructor(repository: PhotosRepository) : ViewModel() {

    var id: Int = 0

    val photo by lazy { repository.observePhoto(id) }

}
