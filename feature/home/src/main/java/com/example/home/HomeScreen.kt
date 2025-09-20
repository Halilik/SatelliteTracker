package com.example.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults.InputField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.designsystem.Black
import com.example.designsystem.Grey
import com.example.designsystem.SatelliteTrackerTheme
import com.example.model.SatellitesModel
import com.example.designsystem.R
import com.example.designsystem.White

@Composable
internal fun HomeScreenRoot(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    navigateToDetail: (Int, String) -> Unit,
) {
    val satellitesState by viewModel.satellitesState.collectAsStateWithLifecycle()
    HomeScreen(
        satellitesState,
        onQueryChange = { query ->
            viewModel.onSearchQueryChanged(query)
        },
        navigateToDetail = navigateToDetail
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    satellitesState: SatellitesState,
    onQueryChange: (String) -> Unit,
    navigateToDetail: (Int, String) -> Unit,
) {
    var searchText by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
    ) {
        Column {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = if (expanded) 0.dp else 16.dp,
                        vertical = 8.dp
                    ),
                inputField = {
                    InputField(
                        query = searchText,
                        onQueryChange = {
                            searchText = it
                            onQueryChange(it)
                        },
                        onSearch = {

                        },
                        expanded = false,
                        onExpandedChange = {
                        },
                        placeholder = { Text("Search Satellites") },
                        leadingIcon = {
                            Icon(
                                Icons.Filled.Search,
                                contentDescription = "Search Icon"
                            )
                        })
                },
                expanded = false,
                onExpandedChange = { }
            ) {

            }

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
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 20.dp),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        satellitesState.data.let {
                            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                                itemsIndexed(
                                    items = it,
                                    key = { index, _ -> index }
                                ) { index, satelliteInfo ->
                                    Column() {
                                        SatelliteElement(
                                            navigateToDetail = navigateToDetail,
                                            satelliteInfo = satelliteInfo
                                        )
                                        if (index != it.lastIndex) {
                                            HorizontalDivider(
                                                modifier = Modifier.padding(
                                                    horizontal = 24.dp,
                                                    vertical = 10.dp
                                                ),
                                                thickness = 1.dp,
                                                color = Color.LightGray
                                            )
                                        }

                                    }
                                }
                            }
                        }
                    }
                }


                is SatellitesState.Error -> Unit
            }
        }
    }


}

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun SatelliteElement(
    satelliteInfo: SatellitesModel,
    navigateToDetail: (Int, String) -> Unit
) {

    val configuration = LocalConfiguration.current
    val startPadding = remember(configuration.screenWidthDp) {
        val itemSize = 100.dp
        (configuration.screenWidthDp.dp - itemSize) / 2
    }

    Row(
        modifier = Modifier
            .padding(start = startPadding)
            .clickable {
                satelliteInfo.id?.let { navigateToDetail.invoke(it, satelliteInfo.name.orEmpty()) }
            }
    ) {
        Image(
            modifier = Modifier
                .size(15.dp),
            painter = if (satelliteInfo.active == true)
                painterResource(id = R.drawable.greencircle)
            else painterResource(
                id = R.drawable.redcircle
            ),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(20.dp))
        Column {
            Text(
                modifier = Modifier.padding(bottom = 4.dp),
                color = setSatelliteTextColor(satelliteInfo.active.toString()),
                text = satelliteInfo.name.orEmpty()
            )
            Text(
                color = setSatelliteTextColor(satelliteInfo.active.toString()),
                text = setSatelliteActivity(satelliteInfo.active.toString())
            )
        }
    }
}

fun setSatelliteTextColor(active: String): Color {
    return if (active == "true")
        Black
    else
        Grey
}

fun setSatelliteActivity(active: String): String {
    return if (active == "true")
        "Active"
    else
        "Passive"
}

@Preview(showBackground = true)
@Composable
internal fun GreetingPreview() {
    SatelliteTrackerTheme {
        HomeScreen(
            SatellitesState.Success(PreviewAndTestData.dummySatellitesList),
            {},
            { model, name -> })
    }
}
