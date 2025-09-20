package com.example.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SatelliteDetailDao {
    @Query("SELECT * FROM satellite_detail WHERE id = :id")
    suspend fun getSatelliteDetailFromId(id: Int): SatelliteDetail?

    @Insert
    suspend fun insertSatelliteDetail(satellites: SatelliteDetail)
}