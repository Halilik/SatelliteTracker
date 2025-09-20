package com.example.detail

import com.example.model.PositionsDataModel
import com.example.model.SatellitesDetailModel

object PreviewAndTestData {
    val dummySatelliteDetail = SatellitesDetailModel(
        id = 1,
        name = "Starship-1",
        costPerLaunch = 7200000,
        firstFlight = "2021-12-01",
        height = 118,
        mass = 1167000

    )

    val dummyPositionsDataModel = PositionsDataModel(
        posX = 12.34,
        posY = 56.78
    )
}
