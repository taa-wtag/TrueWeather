package com.rektstudios.trueweather.di

import com.rektstudios.trueweather.data.local.IRealmDao
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DaoModule {
    @Binds
    abstract fun bindRealmDao(
        realmDaoImpl: IRealmDao
    ): IRealmDao
}