package com.alexrdclement.uiplayground.app.demo.components.geometry.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.alexrdclement.uiplayground.app.catalog.CatalogScreen
import com.alexrdclement.uiplayground.app.configuration.ConfigureButton
import com.alexrdclement.uiplayground.app.demo.popBackStackIfResumed
import kotlinx.serialization.Serializable

@Serializable
object GeometryComponentsGraphRoute

@Serializable
object GeometryComponentCatalogRoute

fun NavGraphBuilder.geometryComponentsGraph(
    navController: NavController,
    onConfigureClick: () -> Unit,
) {
    navigation<GeometryComponentsGraphRoute>(
        startDestination = GeometryComponentCatalogRoute,
    ) {
        geometryComponentCatalogScreen(
            onItemClick = navController::navigateToComponent,
            onNavigateBack = navController::popBackStackIfResumed,
            onConfigureClick = onConfigureClick,
        )
        componentScreen(
            onNavigateBack = navController::popBackStackIfResumed,
            onConfigureClick = onConfigureClick,
        )
    }
}

fun NavController.navigateToGeometryComponents() {
    this.navigate(GeometryComponentsGraphRoute) {
        launchSingleTop = true
    }
}

private fun NavGraphBuilder.geometryComponentCatalogScreen(
    onItemClick: (GeometryComponent) -> Unit,
    onNavigateBack: () -> Unit,
    onConfigureClick: () -> Unit,
) {
    composable<GeometryComponentCatalogRoute> {
        CatalogScreen(
            items = GeometryComponent.entries.toList(),
            onItemClick = onItemClick,
            title = "Geometry",
            onNavigateBack = onNavigateBack,
            actions = {
                ConfigureButton(onClick = onConfigureClick)
            },
        )
    }
}
