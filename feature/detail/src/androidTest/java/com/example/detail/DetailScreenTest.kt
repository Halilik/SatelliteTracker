package com.example.detail

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.example.designsystem.SatelliteTrackerTheme
import org.junit.Rule
import org.junit.Test

class DetailScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun splashScreen_DisplayedCorrectly_onLoading() {
        composeTestRule.setContent {
            SatelliteTrackerTheme {
                DetailScreen(
                    satellitesDetailState = SatellitesDetailState.Loading,
                    satellitesPosition = PreviewAndTestData.dummyPositionsDataModel
                )
            }
        }
        composeTestRule.onNodeWithTag(DetailComponentKey.CIRCULAR_PROGRESS_INDICATOR)
            .assertIsDisplayed()
    }

    @Test
    fun splashScreen_DisplayedCorrectly() {
        composeTestRule.setContent {
            SatelliteTrackerTheme {
                DetailScreen(
                    satellitesDetailState = SatellitesDetailState.Success(PreviewAndTestData.dummySatelliteDetail),
                    satellitesPosition = PreviewAndTestData.dummyPositionsDataModel
                )
            }
        }
        composeTestRule.onNodeWithTag(DetailComponentKey.CIRCULAR_PROGRESS_INDICATOR)
            .assertIsNotDisplayed()

        composeTestRule.onNodeWithTag(DetailComponentKey.SATELLITE_NAME)
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(DetailComponentKey.FIRST_FLIGHT)
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("Height/Mass:")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("Cost:")
            .assertIsDisplayed()
        composeTestRule.onNodeWithText("Last Position:")
            .assertIsDisplayed()
    }

}