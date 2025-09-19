package com.example.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.home.HomeScreenRoot
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

fun NavController.navigateToHome(navOptions: NavOptions? = null) =
    navigate(route = HomeRoute, navOptions = navOptions)

fun NavGraphBuilder.homeScreen(
   // navigateToCategory: (String) -> Unit
) {

    composable<HomeRoute> {
        HomeScreenRoot(
            //navigateToCategory = navigateToCategory
     )
    }
}