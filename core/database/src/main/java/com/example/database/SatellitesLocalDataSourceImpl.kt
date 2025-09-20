package com.example.database

import javax.inject.Inject

class SatellitesLocalDataSourceImpl @Inject constructor(
    private val satelliteDetailDao: SatelliteDetailDao
) : SatellitesLocalDataSource {
    override suspend fun getSatelliteDetailFromId(id: Int): SatelliteDetail? =
        satelliteDetailDao.getSatelliteDetailFromId(id)

    override suspend fun insertSatelliteDetail(satelliteDetail: SatelliteDetail) =
        satelliteDetailDao.insertSatelliteDetail(satelliteDetail)
}