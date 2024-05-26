package com.rektstudios.trueweather.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.rektstudios.trueweather.repositories.IPrefsDataStoreRepository
import com.rektstudios.trueweather.repositories.UserPrefsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

val Context.prefsDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "user_preferences"
)
@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreModule {
    @Binds
    @Singleton
    abstract fun bindPrefsDataStoreRepository(
        userPrefsRepository: UserPrefsRepository
    ): IPrefsDataStoreRepository

    companion object {
        @Provides
        @Singleton
        fun providePrefsDataStore(
            @ApplicationContext applicationContext: Context
        ): DataStore<Preferences> {
            return applicationContext.prefsDataStore
        }
    }
}