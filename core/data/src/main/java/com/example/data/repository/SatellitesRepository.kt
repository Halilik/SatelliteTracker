package com.example.data.repository

import com.example.model.PositionsResponseModel
import com.example.model.SatellitesDetailModel
import com.example.model.SatellitesModel

interface SatellitesRepository {

    suspend fun getSatellites(): List<SatellitesModel>

    suspend fun getSatellitesDetails(id: Int, name: String): SatellitesDetailModel?

    suspend fun getSatellitesPositions(): PositionsResponseModel

}