package com.example.network.datasource

import com.example.model.SatellitesModel


import android.content.Context
import com.example.model.PositionsResponseModel
import com.example.model.SatellitesDetailModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import java.io.IOException
import java.nio.charset.Charset
import javax.inject.Inject

class SatellitesRemoteDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : SatellitesRemoteDataSource {
    override suspend fun getSatellites(): List<SatellitesModel> {
        val jsonString = getDataFromJsonFile(SATELLITES_ASSET)

        return if (jsonString != null) {
            Json.decodeFromString<List<SatellitesModel>>(jsonString)
        } else {
            emptyList()
        }
    }

    override suspend fun getSatellitesDetails(): List<SatellitesDetailModel> {
        val jsonString = getDataFromJsonFile(SATELLITES_DETAIL_ASSET)

        return if (jsonString != null) {
            Json.decodeFromString<List<SatellitesDetailModel>>(jsonString)
        } else {
            emptyList()
        }
    }

    override suspend fun getSatellitesPositions(): PositionsResponseModel {
        val jsonString = getDataFromJsonFile(SATELLITES_POSITIONS_ASSET)

        return if (jsonString != null) {
            Json.decodeFromString<PositionsResponseModel>(jsonString)
        } else {
            PositionsResponseModel()
        }
    }

    private fun getDataFromJsonFile(fileName: String): String? {
        return try {
            context.assets.open(fileName).bufferedReader(Charset.defaultCharset())
                .use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            null
        }
    }

    companion object {
        private const val SATELLITES_ASSET = "satellites.json"
        private const val SATELLITES_DETAIL_ASSET = "satellites-detail.json"
        private const val SATELLITES_POSITIONS_ASSET = "positions.json"

    }
}
