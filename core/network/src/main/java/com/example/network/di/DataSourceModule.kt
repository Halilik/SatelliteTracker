package com.example.network.di

import android.content.Context
import com.example.network.datasource.SatellitesRemoteDataSource
import com.example.network.datasource.SatellitesRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    fun provideSatellitesDataSource(@ApplicationContext context: Context): SatellitesRemoteDataSource {
        return SatellitesRemoteDataSourceImpl(context)
    }
}