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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.designsystem.Black
import com.example.designsystem.Dimens
import com.example.designsystem.Grey
import com.example.designsystem.SatelliteTrackerTheme
import com.example.model.SatellitesModel
import com.example.designsystem.R
import com.example.designsystem.White
import com.example.home.R.*

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
    var expanded by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .semantics { testTagsAsResourceId = true }
            .background(color = White)
    ) {
        Column {
            SearchBar(expanded, onQueryChange)

            when (satellitesState) {
                SatellitesState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier.testTag(HomeComponentKey.CIRCULAR_PROGRESS_INDICATOR))
                    }
                }

                is SatellitesState.Success -> {
                    SatelliteListView(satellitesState, navigateToDetail)
                }


                is SatellitesState.Error -> Unit
            }
        }
    }


}

@Composable
private fun SatelliteListView(
    satellitesState: SatellitesState.Success,
    navigateToDetail: (Int, String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = Dimens.paddingLarger),
        contentAlignment = Alignment.TopCenter
    ) {
        satellitesState.data.let {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(HomeComponentKey.LAZY_COLUMN)
            ) {
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
                                    horizontal = Dimens.paddingLargest,
                                    vertical = Dimens.paddingSmall
                                ),
                                thickness = Dimens.thicknessSmall,
                                color = Color.LightGray
                            )
                        }

                    }
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun SearchBar(
    expanded: Boolean,
    onQueryChange: (String) -> Unit
) {
    val searchTextLocal = remember { mutableStateOf("") }
    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(HomeComponentKey.SEARCH_BAR)
            .padding(
                horizontal = if (expanded) Dimens.paddingZero else Dimens.paddingNormal,
                vertical = Dimens.paddingSmaller
            ),
        inputField = {
            InputField(
                query = searchTextLocal.value,
                onQueryChange = {
                    searchTextLocal.value = it
                    onQueryChange(it)
                },
                onSearch = {

                },
                expanded = false,
                onExpandedChange = {
                },
                placeholder = { Text(stringResource(string.search_satellites)) },
                leadingIcon = {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = stringResource(string.search_icon)
                    )
                })
        },
        expanded = false,
        onExpandedChange = { }
    ) {

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
        val itemSize = Dimens.sizeLarge
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
                .size(Dimens.sizeSmaller),
            painter = if (satelliteInfo.active == true)
                painterResource(id = R.drawable.greencircle)
            else painterResource(
                id = R.drawable.redcircle
            ),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(Dimens.sizeSmall))
        Column {
            Text(
                modifier = Modifier.padding(bottom = Dimens.paddingSmallest),
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
    return if (active == "true") {
        "Active"
    } else
        "Passive"
}

@Preview(showBackground = true)
@Composable
internal fun HomeScreenPreview() {
    SatelliteTrackerTheme {
        HomeScreen(
            SatellitesState.Success(PreviewAndTestData.dummySatellitesList),
            {},
            { model, name -> })
    }
}
