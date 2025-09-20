package com.example.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performScrollToIndex
import com.example.designsystem.SatelliteTrackerTheme
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun splashScreen_DisplayedCorrectly_onLoading() {
        composeTestRule.setContent {
            SatelliteTrackerTheme {
                HomeScreen(
                    satellitesState = SatellitesState.Loading,
                    onQueryChange = { },
                    navigateToDetail = { id, name -> },
                )
            }
        }
        composeTestRule.onNodeWithTag(HomeComponentKey.CIRCULAR_PROGRESS_INDICATOR)
            .assertIsDisplayed()

    }

    @Test
    fun splashScreen_DisplayedCorrectly_onSuccess() {
        composeTestRule.setContent {
            SatelliteTrackerTheme {
                HomeScreen(
                    satellitesState = SatellitesState.Success(PreviewAndTestData.dummySatellitesList),
                    onQueryChange = { },
                    navigateToDetail = { id, name -> },
                )
            }
        }
        composeTestRule.onNodeWithTag(HomeComponentKey.CIRCULAR_PROGRESS_INDICATOR)
            .assertIsNotDisplayed()
        composeTestRule.onNodeWithTag(HomeComponentKey.SEARCH_BAR).assertIsDisplayed()
        composeTestRule.onNodeWithTag(HomeComponentKey.LAZY_COLUMN).assertIsDisplayed()
        composeTestRule.onNodeWithTag(HomeComponentKey.LAZY_COLUMN).performScrollToIndex(1)

    }

}