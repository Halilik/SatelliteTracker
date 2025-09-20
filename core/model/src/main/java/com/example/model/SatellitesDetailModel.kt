package com.example.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SatellitesDetailModel(
    var name: String? = null,
    val id: Int? = null,
    @SerialName("cost_per_launch") val costPerLaunch: Long? = null,
    @SerialName("first_flight") val firstFlight: String? = null,
    val height: Int? = null,
    val mass: Int? = null
)
