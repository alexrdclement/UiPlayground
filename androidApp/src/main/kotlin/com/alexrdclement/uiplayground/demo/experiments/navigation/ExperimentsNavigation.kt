package com.alexrdclement.uiplayground.demo.experiments.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.alexrdclement.uiplayground.catalog.CatalogScreen
import com.alexrdclement.uiplayground.demo.experiments.Experiment

private const val experimentsGraphRoute = "experimentsGraph"
private const val experimentCatalogRoute = "experimentCatalog"

fun NavGraphBuilder.experimentsGraph(
    navController: NavController,
) {
    navigation(
        route = experimentsGraphRoute,
        startDestination = experimentCatalogRoute,
    ) {
        experimentCatalogScreen(
            onItemClick = navController::navigateToExperiment,
        )
        experimentScreen()
    }
}

fun NavController.navigateToExperiments() {
    this.navigate(experimentsGraphRoute) {
        launchSingleTop = true
    }
}

private fun NavGraphBuilder.experimentCatalogScreen(
    onItemClick: (Experiment) -> Unit,
) {
    composable(experimentCatalogRoute) {
        CatalogScreen(
            items = Experiment.entries.toList(),
            onItemClick = onItemClick,
        )
    }
}
