package com.example.database

interface SatellitesLocalDataSource {

    suspend fun getSatelliteDetailFromId(id: Int): SatelliteDetail?

    suspend fun insertSatelliteDetail(satelliteDetail: SatelliteDetail)

}