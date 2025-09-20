package com.example.data.di

import com.example.data.repository.SatellitesRepository
import com.example.data.repository.SatellitesRepositoryImpl
import com.example.database.SatelliteDatabase
import com.example.database.SatelliteDetailDao
import com.example.network.datasource.SatellitesRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideSatellitesRepository(
        satellitesRemoteDataSource: SatellitesRemoteDataSource,
        satelliteDetailDao: SatelliteDetailDao
    ): SatellitesRepository {
        return SatellitesRepositoryImpl(satellitesRemoteDataSource, satelliteDetailDao)
    }
}