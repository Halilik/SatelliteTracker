package com.example.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import com.example.home.HomeScreenRoot
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

fun NavController.navigateToHome(navOptions: NavOptions? = null) =
    navigate(route = HomeRoute) {
        popUpToTop(this@navigateToHome)
    }

fun NavGraphBuilder.homeScreen(
    navigateToDetail: (Int, String) -> Unit
) {

    composable<HomeRoute> {
        HomeScreenRoot(
            navigateToDetail = navigateToDetail
        )
    }
}

fun NavOptionsBuilder.popUpToTop(navController: NavController) {
    popUpTo(navController.currentBackStackEntry?.destination?.route ?: return) {
        inclusive = true
    }
}