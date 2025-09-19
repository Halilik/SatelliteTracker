package com.example.satellitetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.home.navigation.homeScreen
import com.example.home.navigation.navigateToHome
import com.example.splash.navigation.SplashRoute
import com.example.splash.navigation.splashScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = SplashRoute
                    ) {
                        splashScreen(
                            navigateToHome = navController::navigateToHome
                        )
                        homeScreen(
                            // navigateToCategory = navController::navigateToCategory
                        )

                    }
                }
            }
        }
    }
}

