package com.example.detail

import android.icu.text.NumberFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.designsystem.BoldFont
import com.example.designsystem.RegularFont
import com.example.designsystem.SatelliteTrackerTheme
import com.example.designsystem.SemiBoldFont
import com.example.designsystem.White
import com.example.model.PositionsDataModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale.GERMANY

@Composable
internal fun DetailScreenRoot(
    satelliteId: Int, satelliteName: String, viewModel: DetailScreenViewModel = hiltViewModel()
) {
    LaunchedEffect(satelliteName) {
        viewModel.setSatelliteName(satelliteName)
    }
    LaunchedEffect(satelliteId) {
        viewModel.getSatellitesDetail(satelliteId)
        viewModel.getSatellitesPosition(satelliteId)
    }
    val satelliteDetailState by viewModel.satelliteDetailState.collectAsStateWithLifecycle()
    val satellitePositionState by viewModel.satellitePositionState.collectAsStateWithLifecycle()
    DetailScreen(
        satelliteDetailState, satellitePositionState
    )
}

@Composable
internal fun DetailScreen(
    satellitesDetailState: SatellitesDetailState, satellitesPosition: PositionsDataModel
) {

    when (satellitesDetailState) {
        SatellitesDetailState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is SatellitesDetailState.Error -> Unit

        is SatellitesDetailState.Success -> {
            satellitesDetailState.data.let { satellite ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(White),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            text = satellite.name.toString(),
                            fontFamily = BoldFont,
                            fontSize = 28.sp,
                            textAlign = TextAlign.Center,
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 40.dp),
                            text = satellite.firstFlight.toString().dateFormat(),
                            fontFamily = RegularFont,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        )
                        InfoElement("Height/Mass: ", "${satellite.height}/${satellite.mass}")
                        InfoElement("Cost:", satellite.costPerLaunch.toString())
                        InfoElement(
                            "LastPosition: ",
                            "(${satellitesPosition.posX},${satellitesPosition.posY})"
                        )


                    }
                }
            }
        }
    }
}

@Composable
private fun InfoElement(title: String, text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Row {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = text.formatNumber(),
                fontFamily = RegularFont,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

fun String.dateFormat(): String {
    val dateString = "2021-12-01"
    val inputFormatter = DateTimeFormatter.ISO_LOCAL_DATE
    val outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    val date = LocalDate.parse(dateString, inputFormatter)
    val formattedDate = date.format(outputFormatter)
    return formattedDate
}

fun String.formatNumber(): String {
    return try {
        val number = this.toLong()
        val numberFormat = NumberFormat.getNumberInstance(GERMANY)
        numberFormat.format(number)
    } catch (e: NumberFormatException) {
        this
    }
}

@Preview(showBackground = true)
@Composable
internal fun DetailScreenPreview(
) {
    SatelliteTrackerTheme {
        DetailScreen(
            SatellitesDetailState.Success(PreviewAndTestData.dummySatelliteDetail),
            PreviewAndTestData.dummyPositionsDataModel
        )
    }

}