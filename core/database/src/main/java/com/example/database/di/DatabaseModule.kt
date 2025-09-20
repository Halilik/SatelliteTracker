package com.example.database.di

import android.content.Context
import androidx.room.Room
import com.example.database.SatelliteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

object DatabaseModule {

    @Module
    @InstallIn(SingletonComponent::class)
    internal object DatabaseModule {
        @Provides
        @Singleton
        fun providesNiaDatabase(
            @ApplicationContext context: Context,
        ): SatelliteDatabase = Room.databaseBuilder(
            context,
            SatelliteDatabase::class.java,
            "satelite-database",
        ).build()
    }

}