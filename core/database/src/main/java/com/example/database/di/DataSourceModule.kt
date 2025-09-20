package com.example.database.di

import com.example.database.SatelliteDetailDao
import com.example.database.SatellitesLocalDataSource
import com.example.database.SatellitesLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    fun provideSatellitesLocalDataSource(satelliteDetailDao: SatelliteDetailDao): SatellitesLocalDataSource {
        return SatellitesLocalDataSourceImpl(satelliteDetailDao)
    }
}