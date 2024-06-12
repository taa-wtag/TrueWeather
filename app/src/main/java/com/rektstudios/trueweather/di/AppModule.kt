package com.rektstudios.trueweather.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.rektstudios.trueweather.data.local.IRealmDao
import com.rektstudios.trueweather.data.remote.MapBoxApiService
import com.rektstudios.trueweather.data.remote.WeatherApiService
import com.rektstudios.trueweather.other.Constants.MAPBOX_BASE_URL
import com.rektstudios.trueweather.other.Constants.WEATHER_BASE_URL
import com.rektstudios.trueweather.repositories.CityRepositoryImpl
import com.rektstudios.trueweather.repositories.ICityRepository
import com.rektstudios.trueweather.repositories.IPrefsRepository
import com.rektstudios.trueweather.repositories.IWeatherRepository
import com.rektstudios.trueweather.repositories.PrefsRepositoryImpl
import com.rektstudios.trueweather.repositories.WeatherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.realm.Realm
import io.realm.RealmConfiguration
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePrefsRepository(
        prefsDataStore: DataStore<Preferences>
    ): IPrefsRepository = PrefsRepositoryImpl(prefsDataStore)

    @Singleton
    @Provides
    fun provideWeatherApi(): WeatherApiService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(WEATHER_BASE_URL)
            .build()
            .create(WeatherApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideMapboxApi(): MapBoxApiService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(MAPBOX_BASE_URL)
            .build()
            .create(MapBoxApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesRealmDatabase(
        @ApplicationContext context: Context
    ): Realm {
        Realm.init(context)
        val realmConfiguration = RealmConfiguration
            .Builder()
            .name("True Weather App")
            .build()
        Realm.setDefaultConfiguration(realmConfiguration)
        return Realm.getDefaultInstance()
    }
    @Singleton
    @Provides
    fun provideWeatherRepository(
        weatherApiService: WeatherApiService,
        realmDao: IRealmDao
    ) = WeatherRepositoryImpl(weatherApiService,realmDao) as IWeatherRepository

    @Singleton
    @Provides
    fun provideCityRepository(
        mapBoxApiService: MapBoxApiService,
        realmDao: IRealmDao
    ) = CityRepositoryImpl(mapBoxApiService,realmDao) as ICityRepository
}