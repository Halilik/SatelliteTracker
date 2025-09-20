package com.example.database.di

import com.example.database.SatelliteDatabase
import com.example.database.SatelliteDetailDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {
    @Provides
    fun providesSatelliteDetailDao(
        database: SatelliteDatabase,
    ): SatelliteDetailDao = database.satelliteDetailDao()

}
