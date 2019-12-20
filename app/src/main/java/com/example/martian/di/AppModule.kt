package com.example.martian.di

import android.app.Application
import com.example.martian.api.MarsApiService
import com.example.martian.data.AppDatabase
import com.example.martian.rover_list.data.RoverRemoteDataSource
import com.example.martian.rover_photos.data.PhotosRemoteDataSource
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class, CoreDataModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideNasaService(@NasaAPI okhttpClient: OkHttpClient,
                           converterFactory: GsonConverterFactory
    ) = provideService(okhttpClient, converterFactory, MarsApiService::class.java)

    @Singleton
    @Provides
    fun providePhotosRemoteDataSource(photoService: MarsApiService)
            = PhotosRemoteDataSource(photoService)

    @Singleton
    @Provides
    fun provideRoverRemoteDataSource(marsApiService: MarsApiService)
            = RoverRemoteDataSource(marsApiService)

    @NasaAPI
    @Provides
    fun providePrivateOkHttpClient(
            upstreamClient: OkHttpClient
    ): OkHttpClient {
        return upstreamClient.newBuilder().build()
    }

    @Singleton
    @Provides
    fun provideDb(app: Application) = AppDatabase.getInstance(app)

    @Singleton
    @Provides
    fun providePhotosDao(db: AppDatabase) = db.photosDao()


    @Singleton
    @Provides
    fun provideRoverDao(db: AppDatabase) = db.roversDao()

    @CoroutineScropeIO
    @Provides
    fun provideCoroutineScopeIO() = CoroutineScope(Dispatchers.IO)


    private fun createRetrofit(
            okhttpClient: OkHttpClient,
            converterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
                .baseUrl(MarsApiService.ENDPOINT)
                .client(okhttpClient)
                .addConverterFactory(converterFactory)
                .build()
    }

    private fun <T> provideService(okhttpClient: OkHttpClient,
            converterFactory: GsonConverterFactory, clazz: Class<T>): T {
        return createRetrofit(okhttpClient, converterFactory).create(clazz)
    }
}
