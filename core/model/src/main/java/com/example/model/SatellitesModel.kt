package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class SatellitesModel(
    val id: Int? = null,
    val active: Boolean? = null,
    val name: String? = null
)
