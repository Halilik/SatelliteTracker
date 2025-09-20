package com.example.splash

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.example.designsystem.SatelliteTrackerTheme
import org.junit.Rule
import org.junit.Test

class SplashScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun splashScreen_DisplayedCorrectly() {
        composeTestRule.setContent {
            SatelliteTrackerTheme {
                SplashScreen({}, SplashUiState(isLoading = false))
            }
        }
        composeTestRule.onNodeWithTag(SplashComponentKey.SPLASH_ICON).assertIsDisplayed()
    }

}