package com.example.martian.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.martian.photo.PhotoViewModel
import com.example.martian.rover_list.ui.RoverViewModel
import com.example.martian.rover_photos.ui.RoverPhotosViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(RoverPhotosViewModel::class)
    abstract fun bindRoverPhotosViewModel(viewModel: RoverPhotosViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PhotoViewModel::class)
    abstract fun bindPhotoViewModel(viewModel: PhotoViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(RoverViewModel::class)
    abstract fun bindRoverViewModel(viewModel: RoverViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}