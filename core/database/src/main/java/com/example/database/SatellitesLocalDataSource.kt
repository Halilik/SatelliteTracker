package com.example.database

import androidx.room.Insert

interface SatellitesLocalDataSource {

    suspend fun getSatelliteDetailFromId(id: Int): SatelliteDetail?

}