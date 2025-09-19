package com.example.data.repository

import com.example.model.SatellitesModel
import com.example.network.datasource.SatellitesRemoteDataSource
import javax.inject.Inject

class SatellitesRepositoryImpl @Inject constructor(
    private val satellitesRemoteDataSource: SatellitesRemoteDataSource
) : SatellitesRepository {
    override suspend fun getSatellites(): List<SatellitesModel> =
        satellitesRemoteDataSource.getSatellites()

    override suspend fun getSatellitesDetails(): List<SatellitesModel> =
        satellitesRemoteDataSource.getSatellitesDetails()


    override suspend fun getSatellitesPositions(): List<SatellitesModel> =
        satellitesRemoteDataSource.getSatellitesPositions()

}