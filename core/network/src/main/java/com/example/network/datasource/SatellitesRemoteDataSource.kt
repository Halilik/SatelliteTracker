package com.example.network.datasource

import com.example.model.SatellitesModel

interface SatellitesRemoteDataSource {
    suspend fun getSatellites(): List<SatellitesModel>

    suspend fun getSatellitesDetails(): List<SatellitesModel>

    suspend fun getSatellitesPositions(): List<SatellitesModel>

}