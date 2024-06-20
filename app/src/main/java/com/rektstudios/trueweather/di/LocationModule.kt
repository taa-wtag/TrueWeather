package com.rektstudios.trueweather.di

import com.rektstudios.trueweather.data.location.LocationTrackerImpl
import com.rektstudios.trueweather.domain.location.ILocationTracker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {

    @Binds
    @Singleton
    abstract fun bindLocationTracker(
        locationTrackerImpl: LocationTrackerImpl
    ): ILocationTracker

}