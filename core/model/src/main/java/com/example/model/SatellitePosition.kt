package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class PositionsResponseModel(

    var list: List<PositionsElementModel?>? = null

)

@Serializable
data class PositionsElementModel(
    val id: String? = null,
    val positions: List<PositionsDataModel?>? = null
)


@Serializable
data class PositionsDataModel(
    val posX: Double? = null,
    val posY: Double? = null
)