package com.example.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SatelliteDetail::class], version = 1)
abstract class SatelliteDatabase : RoomDatabase() {
    abstract fun satelliteDetailDao(): SatelliteDetailDao
}