package com.example.martian.repository

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.martian.api.MarsApiService
import com.example.martian.data.AppDatabase
import com.example.martian.rover_photos.data.PhotosDao
import com.example.martian.rover_photos.data.PhotosRemoteDataSource
import com.example.martian.rover_photos.data.PhotosRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*

@RunWith(JUnit4::class)
class PhotosRepositoryTest {
    private lateinit var repository: PhotosRepository
    private val dao = mock(PhotosDao::class.java)
    private val service = mock(MarsApiService::class.java)
    private val remoteDataSource = PhotosRemoteDataSource(service)
//    private val mockRemoteDataSource = spy(remoteDataSource)

    private val roverName = "Curiosity"
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    @Before
    fun init() {
        val db = mock(AppDatabase::class.java)
        `when`(db.photosDao()).thenReturn(dao)
        `when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()
        repository = PhotosRepository(dao, remoteDataSource)
    }

    @Test
    fun loadPhotosFromNetwork() {
        runBlocking {
            repository.observePagedPhotos(connectivityAvailable = true,
                roverName = roverName, coroutineScope = coroutineScope)

            verify(dao, never()).getPhotos(roverName)
            verifyZeroInteractions(dao)
        }
    }

}