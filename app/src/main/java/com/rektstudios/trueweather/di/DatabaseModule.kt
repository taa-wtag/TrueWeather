package com.rektstudios.trueweather.di

import android.content.Context
import androidx.room.Room
import com.rektstudios.trueweather.data.local.WeatherDatabase
import com.rektstudios.trueweather.data.local.dao.IDatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideWeatherDatabase(
        @ApplicationContext context: Context,
    ): WeatherDatabase =
        Room
            .databaseBuilder(
                context,
                WeatherDatabase::class.java,
                WeatherDatabase.DATABASE_NAME,
            ).build()

    @Provides
    @Singleton
    fun provideWeatherDao(database: WeatherDatabase): IDatabaseDao = database.weatherDao
}
