package com.example.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.designsystem.SatelliteTrackerTheme
import com.example.model.SatellitesModel

@Composable
internal fun HomeScreenRoot(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    //navigateToDetail: (String) -> Unit,
) {
    val satellitesState by viewModel.satellitesState.collectAsStateWithLifecycle()
    HomeScreen(
        satellitesState
    )
}


@Composable
internal fun HomeScreen(satellitesState: SatellitesState) {
    when (satellitesState) {
        SatellitesState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is SatellitesState.Success -> {
            satellitesState.data.let {
                LazyColumn {
                    itemsIndexed(
                        items = it,
                        key = { index, _ -> index }) { index, satelliteInfo ->
                        SatelliteElement(
                            satelliteInfo = satelliteInfo
                        )
                    }
                }
            }
        }

        is SatellitesState.Error -> Unit
    }
}

@Composable
fun SatelliteElement(satelliteInfo: SatellitesModel) {
    Text(text = satelliteInfo.name.orEmpty())
}

@Preview
@Composable
internal fun GreetingPreview() {
    SatelliteTrackerTheme {
        HomeScreenRoot()
    }
}