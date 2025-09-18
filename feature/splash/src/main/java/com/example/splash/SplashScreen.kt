package com.example.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.designsystem.SatelliteTrackerTheme


@Composable
internal fun SplashScreen(
  //  navigateToHome: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {


    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState.isLoading) {
        true -> {
        //    navigateToHome.invoke()
        }

        false -> {}
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xff666666))
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//            Image(
//                painter = painterResource(id = drawable.splash_icon),
//                contentDescription = null
//            )
        }

    }
}


@Preview
@Composable
internal fun GreetingPreview() {
    SatelliteTrackerTheme {
        SplashScreen()
    }
}