package com.example.home

import com.example.model.SatellitesModel

object PreviewAndTestData {
    val dummySatellitesList = listOf(
        SatellitesModel(
            id = 1,
            active = true,
            name = "Starship-1"
        ),
        SatellitesModel(
            id = 2,
            active = false,
            name = "Dragon-1"
        )
    )
}