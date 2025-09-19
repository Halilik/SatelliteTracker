package com.example.data.repository

import com.example.model.SatellitesModel

interface SatellitesRepository {

    suspend fun getSatellites(): List<SatellitesModel>

    suspend fun getSatellitesDetails(): List<SatellitesModel>

    suspend fun getSatellitesPositions(): List<SatellitesModel>

}