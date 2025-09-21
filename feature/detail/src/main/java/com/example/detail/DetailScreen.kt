package com.example.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.designsystem.Dimens
import com.example.designsystem.SatelliteTrackerTheme
import com.example.designsystem.Typography
import com.example.designsystem.White
import com.example.model.PositionsDataModel
import com.example.ui.dateFormat
import com.example.ui.formatNumber

@Composable
internal fun DetailScreenRoot(
    satelliteId: Int, satelliteName: String, viewModel: DetailScreenViewModel = hiltViewModel()
) {

    LaunchedEffect(satelliteId) {
        viewModel.setSatelliteName(satelliteName)
        viewModel.getSatellitesDetail(satelliteId, satelliteName)
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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .semantics { testTagsAsResourceId = true }) {
        when (satellitesDetailState) {
            SatellitesDetailState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.testTag(
                            DetailComponentKey.CIRCULAR_PROGRESS_INDICATOR
                        )
                    )
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
                                    .testTag(DetailComponentKey.SATELLITE_NAME)
                                    .padding(bottom = Dimens.paddingNormal),
                                text = satellite.name.toString(),
                                style = Typography.titleLarge,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag(DetailComponentKey.FIRST_FLIGHT)
                                    .padding(bottom = Dimens.paddingExtraLarge),
                                text = satellite.firstFlight.toString().dateFormat(),
                                style = Typography.bodyMedium,
                                textAlign = TextAlign.Center
                            )
                            InfoElement(
                                stringResource(R.string.heightMass),
                                "${satellite.height}/${satellite.mass}"
                            )
                            InfoElement(
                                stringResource(R.string.cost), satellite.costPerLaunch.toString()
                            )
                            InfoElement(
                                stringResource(R.string.lastPosition),
                                "(${satellitesPosition.posX},${satellitesPosition.posY})"
                            )
                        }
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
            .padding(bottom = Dimens.paddingLargest),
        contentAlignment = Alignment.Center
    ) {
        Row {
            Text(
               modifier = Modifier.testTag(title), text = title, style = Typography.titleMedium, textAlign = TextAlign.Center
            )
            Text(
                text = text.formatNumber(),
                style = Typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
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