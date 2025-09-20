package com.example.data.repository

import com.example.database.SatelliteDetail
import com.example.database.SatelliteDetailDao
import com.example.model.PositionsResponseModel
import com.example.model.SatellitesDetailModel
import com.example.model.SatellitesModel
import com.example.network.datasource.SatellitesRemoteDataSource
import javax.inject.Inject

class SatellitesRepositoryImpl @Inject constructor(
    private val satellitesRemoteDataSource: SatellitesRemoteDataSource,
    private val satelliteDetailDao: SatelliteDetailDao
) : SatellitesRepository {
    override suspend fun getSatellites(): List<SatellitesModel> =
        satellitesRemoteDataSource.getSatellites()

    override suspend fun getSatellitesDetails(id: Int, name: String): SatellitesDetailModel? {
        val satelliteDetail = satelliteDetailDao.getSatelliteDetailFromId(id)

        if (satelliteDetail?.height != null) {
            return SatellitesDetailModel(
                id = satelliteDetail.id,
                costPerLaunch = satelliteDetail.costPerLaunch,
                firstFlight = satelliteDetail.firstFlight,
                height = satelliteDetail.height,
                mass = satelliteDetail.mass,
                name = name
            )
        } else {
            satellitesRemoteDataSource.getSatellitesDetails().firstOrNull { it.id == id }
                ?.let { satelliteDetail ->
                    satelliteDetailDao.insertSatelliteDetail(
                        SatelliteDetail(
                            id = satelliteDetail.id,
                            costPerLaunch = satelliteDetail.costPerLaunch,
                            firstFlight = satelliteDetail.firstFlight,
                            height = satelliteDetail.height,
                            mass = satelliteDetail.mass,
                            name = name
                        )
                    )
                    return satelliteDetail.apply {
                        this.name = name
                    }
                }
        }
        return null
    }

    override suspend fun getSatellitesPositions(): PositionsResponseModel =
        satellitesRemoteDataSource.getSatellitesPositions()
}