package com.example.martian.api


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class MarsAPIServiceTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: MarsApiService

    private lateinit var mockWebServer: MockWebServer

    var roverName = "Curiosity"

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MarsApiService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun requestPhotos() {
        runBlocking {
            enqueueResponse("photos.json")
            val resultResponse = service.getPhotos(roverName).body()

            val request = mockWebServer.takeRequest()
            assertNotNull(resultResponse)
            assertThat(request.path.split("?")[0], `is`("/rovers/$roverName/photos/"))
        }
    }

    @Test
    fun getPhotosResponse() {
        runBlocking {
            enqueueResponse("photos.json")
            val resultResponse = service.getPhotos(roverName).body()
            val photos = resultResponse!!.photos

            assertThat(photos.size, `is`(25))
        }
    }


    @Test
    fun getPhotoItem() {
        runBlocking {
            enqueueResponse("photos.json")
            val resultResponse = service.getPhotos(roverName).body()
            val photos = resultResponse!!.photos

            val photo = photos[0]
            assertThat(photo.id, `is`(102693))
            assertThat(photo.sol, `is`(1000))
            assertThat(photo.date, `is`("2015-05-30"))
            assertThat(photo.imgSrc, `is`("http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01000/opgs/edr/fcam/FLB_486265257EDR_F0481570FHAZ00323M_.JPG"))
        }
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader?.getResourceAsStream("api-responses/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(mockResponse.setBody(
            source.readString(Charsets.UTF_8))
        )
    }
}
