package com.example.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.compose.runtime.*
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.designsystem.Dimens
import com.example.designsystem.SatelliteTrackerTheme
import com.example.designsystem.R
import com.example.designsystem.White


@Composable
internal fun SplashScreenRoot(
    navigateToHome: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {


    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    SplashScreen(
        navigateToHome,
        uiState
    )

}

@Composable
fun SplashScreen(
    navigateToHome: () -> Unit,
    uiState: SplashUiState
) {
    when (uiState.isLoading) {
        false -> {
            navigateToHome.invoke()
        }

        true -> {}
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .semantics { testTagsAsResourceId = true }
            .background(White)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Image(
                modifier = Modifier
                    .size(Dimens.sizeLargest)
                    .testTag(SplashComponentKey.SPLASH_ICON),
                painter = painterResource(id = R.drawable.satellite),
                contentDescription = null
            )
        }

    }
}


@Preview
@Composable
internal fun SplashScreenPreview() {
    SatelliteTrackerTheme {
        SplashScreen({}, SplashUiState(isLoading = false))
    }
}