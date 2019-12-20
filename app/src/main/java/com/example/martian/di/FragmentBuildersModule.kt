package com.example.martian.di


import com.example.martian.photo.PhotoFragment
import com.example.martian.rover_list.ui.RoversListFragment
import com.example.martian.rover_photos.ui.RoverPhotosFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeRoverPhotosFragment(): RoverPhotosFragment

    @ContributesAndroidInjector
    abstract fun contributeRoversListFragment(): RoversListFragment

    @ContributesAndroidInjector
    abstract fun contributePhotoFragment(): PhotoFragment
}
