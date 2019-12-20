package com.example.martian.ui


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.martian.rover_photos.data.PhotosRepository
import com.example.martian.rover_photos.ui.RoverPhotosViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class RoverPhotosViewModelTest {

    private val roverName = "Curiosity"

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val repository = mock(PhotosRepository::class.java)
    private var viewModel = RoverPhotosViewModel(repository, coroutineScope)

    @Test
    fun testNull() {
        assertThat(viewModel.roverName, notNullValue())
        assertThat(viewModel.connectivityAvailable, notNullValue())
        verify(repository, never()).observePagedPhotos(false, roverName, coroutineScope)
        verify(repository, never()).observePagedPhotos(true, roverName, coroutineScope)
    }

    @Test
    fun doNotFetchWithoutObservers() {
        viewModel.roverName = roverName
        verify(repository, never()).observePagedPhotos(false, roverName, coroutineScope)
    }

    @Test
    fun doNotFetchWithoutObserverOnConnectionChange() {
        viewModel.roverName = roverName
        viewModel.connectivityAvailable = true

        verify(repository, never()).observePagedPhotos(true, roverName, coroutineScope)
    }

}