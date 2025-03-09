package com.alexrdclement.uiplayground.app.demo.experiments.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.alexrdclement.uiplayground.app.catalog.CatalogScreen
import com.alexrdclement.uiplayground.app.configuration.ConfigureButton
import com.alexrdclement.uiplayground.app.demo.experiments.Experiment
import com.alexrdclement.uiplayground.app.demo.popBackStackIfResumed
import kotlinx.serialization.Serializable

@Serializable
object ExperimentsGraphRoute

@Serializable
object ExperimentCatalogRoute

fun NavGraphBuilder.experimentsGraph(
    navController: NavController,
    onConfigureClick: () -> Unit,
) {
    navigation<ExperimentsGraphRoute>(
        startDestination = ExperimentCatalogRoute,
    ) {
        experimentCatalogScreen(
            onItemClick = navController::navigateToExperiment,
            onNavigateBack = navController::popBackStackIfResumed,
            onConfigureClick = onConfigureClick,
        )
        experimentScreen(
            onNavigateBack = navController::popBackStackIfResumed,
            onConfigureClick = onConfigureClick,
        )
    }
}

fun NavController.navigateToExperiments() {
    this.navigate(ExperimentsGraphRoute) {
        launchSingleTop = true
    }
}

private fun NavGraphBuilder.experimentCatalogScreen(
    onItemClick: (Experiment) -> Unit,
    onNavigateBack: () -> Unit,
    onConfigureClick: () -> Unit,
) {
    composable<ExperimentCatalogRoute> {
        CatalogScreen(
            items = Experiment.entries.toList(),
            onItemClick = onItemClick,
            title = "Experiments",
            onNavigateBack = onNavigateBack,
            actions = {
                ConfigureButton(onClick = onConfigureClick)
            }
        )
    }
}
