package com.rektstudios.trueweather.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.rektstudios.trueweather.R
import com.rektstudios.trueweather.data.helper.GeocodeHelper
import com.rektstudios.trueweather.data.local.DBMigration
import com.rektstudios.trueweather.data.local.IRealmDao
import com.rektstudios.trueweather.data.remote.MapBoxApiService
import com.rektstudios.trueweather.data.remote.WeatherApiService
import com.rektstudios.trueweather.domain.util.Constants.MAPBOX_BASE_URL
import com.rektstudios.trueweather.domain.util.Constants.WEATHER_BASE_URL
import com.rektstudios.trueweather.data.repository.CityRepositoryImpl
import com.rektstudios.trueweather.domain.repository.ICityRepository
import com.rektstudios.trueweather.domain.repository.IPrefsRepository
import com.rektstudios.trueweather.domain.repository.IWeatherRepository
import com.rektstudios.trueweather.data.repository.PrefsRepositoryImpl
import com.rektstudios.trueweather.data.repository.WeatherRepositoryImpl
import com.rektstudios.trueweather.di.DaoModule.Companion.schemaVersion
import com.rektstudios.trueweather.domain.helper.IGeocodeHelper
import com.rektstudios.trueweather.domain.location.ILocationTracker
import com.rektstudios.trueweather.domain.usecase.AddCityUseCase
import com.rektstudios.trueweather.domain.usecase.CurrentCityUseCase
import com.rektstudios.trueweather.domain.usecase.DeleteCityUseCase
import com.rektstudios.trueweather.domain.usecase.GetCityListUseCase
import com.rektstudios.trueweather.domain.usecase.GetCityNameFromLocationUseCase
import com.rektstudios.trueweather.domain.usecase.GetCitySuggestionsUseCase
import com.rektstudios.trueweather.domain.usecase.GetCurrentWeatherUseCase
import com.rektstudios.trueweather.domain.usecase.GetForecastWeatherUseCase
import com.rektstudios.trueweather.domain.usecase.UserPrefsUseCase
import com.rektstudios.trueweather.domain.util.Constants.USER_PREFERENCES
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePreferencesDataStore(
        @ApplicationContext appContext: Context
    ): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(SharedPreferencesMigration(appContext, USER_PREFERENCES)),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { appContext.preferencesDataStoreFile(USER_PREFERENCES) }
        )

    @Singleton
    @Provides
    fun provideGeocodeHelper(
        @ApplicationContext context: Context
    ): IGeocodeHelper = GeocodeHelper(context)

    @Provides
    @Singleton
    fun providePrefsRepository(
        prefsDataStore: DataStore<Preferences>
    ): IPrefsRepository = PrefsRepositoryImpl(prefsDataStore)

    @Singleton
    @Provides
    fun provideWeatherApi(): WeatherApiService =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(WEATHER_BASE_URL)
            .build()
            .create(WeatherApiService::class.java)

    @Singleton
    @Provides
    fun provideMapboxApi(): MapBoxApiService =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(MAPBOX_BASE_URL)
            .build()
            .create(MapBoxApiService::class.java)

    @Provides
    @Singleton
    fun providesRealmDatabase(
        @ApplicationContext context: Context
    ): Realm {
        Realm.init(context)
        val realmConfiguration = RealmConfiguration
            .Builder()
            .name("db_weather_app")
            .schemaVersion(schemaVersion)
            .migration(DBMigration())
            .build()
        Realm.setDefaultConfiguration(realmConfiguration)
        return Realm.getDefaultInstance()
    }

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(
        app: Application
    ): FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(app)

    @Singleton
    @Provides
    fun provideWeatherRepository(
        weatherApiService: WeatherApiService,
        realmDao: IRealmDao
    ): IWeatherRepository = WeatherRepositoryImpl(weatherApiService, realmDao)

    @Singleton
    @Provides
    fun provideCityRepository(
        mapBoxApiService: MapBoxApiService,
        realmDao: IRealmDao
    ): ICityRepository = CityRepositoryImpl(mapBoxApiService, realmDao)

    @Singleton
    @Provides
    fun provideAddCityUseCase(
        cityRepository: ICityRepository
    ): AddCityUseCase = AddCityUseCase(cityRepository)

    @Singleton
    @Provides
    fun provideDeleteCityUseCase(
        cityRepository: ICityRepository
    ): DeleteCityUseCase = DeleteCityUseCase(cityRepository)

    @Singleton
    @Provides
    fun provideGetCityListUseCase(
        cityRepository: ICityRepository
    ): GetCityListUseCase = GetCityListUseCase(cityRepository)

    @Singleton
    @Provides
    fun provideGetCityNameFromLocationUseCase(
        weatherRepository: IWeatherRepository,
        geocodeHelper: IGeocodeHelper
    ): GetCityNameFromLocationUseCase =
        GetCityNameFromLocationUseCase(weatherRepository, geocodeHelper)

    @Singleton
    @Provides
    fun provideGetCitySuggestionsUseCase(
        cityRepository: ICityRepository,
        weatherRepository: IWeatherRepository
    ): GetCitySuggestionsUseCase = GetCitySuggestionsUseCase(cityRepository, weatherRepository)

    @Singleton
    @Provides
    fun provideGetCurrentWeatherUseCase(
        weatherRepository: IWeatherRepository
    ): GetCurrentWeatherUseCase = GetCurrentWeatherUseCase(weatherRepository)

    @Singleton
    @Provides
    fun provideGetForecastWeatherUseCase(
        weatherRepository: IWeatherRepository
    ): GetForecastWeatherUseCase = GetForecastWeatherUseCase(weatherRepository)

    @Singleton
    @Provides
    fun provideCurrentCityUseCase(
        cityRepository: ICityRepository,
        prefsRepository: IPrefsRepository,
        locationTracker: ILocationTracker,
        getCityNameFromLocationUseCase: GetCityNameFromLocationUseCase
    ): CurrentCityUseCase = CurrentCityUseCase(
        cityRepository,
        prefsRepository,
        locationTracker,
        getCityNameFromLocationUseCase
    )

    @Singleton
    @Provides
    fun provideUserPrefsUseCase(
        prefsRepository: IPrefsRepository
    ): UserPrefsUseCase = UserPrefsUseCase(prefsRepository)

    @Singleton
    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context
    ): RequestManager = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_image)
    )

}