package com.example.network.datasource

import com.example.model.PositionsResponseModel
import com.example.model.SatellitesDetailModel
import com.example.model.SatellitesModel

interface SatellitesRemoteDataSource {
    suspend fun getSatellites(): List<SatellitesModel>

    suspend fun getSatellitesDetails(): List<SatellitesDetailModel>

    suspend fun getSatellitesPositions(): PositionsResponseModel

}