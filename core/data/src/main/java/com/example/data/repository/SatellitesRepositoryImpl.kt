package com.example.data.repository

import com.example.model.PositionsResponseModel
import com.example.model.SatellitesDetailModel
import com.example.model.SatellitesModel
import com.example.network.datasource.SatellitesRemoteDataSource
import javax.inject.Inject

class SatellitesRepositoryImpl @Inject constructor(
    private val satellitesRemoteDataSource: SatellitesRemoteDataSource
) : SatellitesRepository {
    override suspend fun getSatellites(): List<SatellitesModel> =
        satellitesRemoteDataSource.getSatellites()

    override suspend fun getSatellitesDetails(): List<SatellitesDetailModel> =
        satellitesRemoteDataSource.getSatellitesDetails()


    override suspend fun getSatellitesPositions(): PositionsResponseModel =
        satellitesRemoteDataSource.getSatellitesPositions()

}