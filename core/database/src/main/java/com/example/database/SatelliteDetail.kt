package com.example.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "satellite_detail")
data class SatelliteDetail(
    @PrimaryKey val id: Int?,
    @ColumnInfo(name = "cost_per_launch") val costPerLaunch: Long?,
    @ColumnInfo(name = "first_flight") val firstFlight: String?,
    @ColumnInfo(name = "name") var name: String? = null,
    @ColumnInfo(name = "height") val height: Int? = null,
    @ColumnInfo(name = "mass") val mass: Int? = null
)
