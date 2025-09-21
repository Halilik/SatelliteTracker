package com.example.detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.detail.DetailScreenRoot
import kotlinx.serialization.Serializable

@Serializable
data class DetailRoute(
    val categoryId: Int,
    val satelliteName: String
)

fun NavController.navigateToDetail(
    categoryId: Int,
    satelliteName: String,
    navOptions: NavOptions? = null
) = navigate(
    route = DetailRoute(categoryId = categoryId, satelliteName = satelliteName),
    navOptions = navOptions
)

fun NavGraphBuilder.detailScreen(
) {
    composable<DetailRoute> { entry ->
        val satelliteId = entry.toRoute<DetailRoute>().categoryId
        val satelliteName = entry.toRoute<DetailRoute>().satelliteName

        DetailScreenRoot(
            satelliteId,
            satelliteName
        )
    }
}