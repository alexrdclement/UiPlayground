package com.alexrdclement.uiplayground.app.demo.experiments.navigation

import androidx.compose.runtime.Composable
import com.alexrdclement.uiplayground.app.catalog.CatalogScreen
import com.alexrdclement.uiplayground.app.configuration.ConfigureButton
import com.alexrdclement.uiplayground.app.configuration.navigation.ConfigurationRoute
import com.alexrdclement.uiplayground.app.demo.experiments.Experiment
import com.alexrdclement.uiplayground.app.navigation.NavController
import com.alexrdclement.uiplayground.app.navigation.UiPlaygroundNavKey
import com.alexrdclement.uiplayground.app.navigation.toPathSegment
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("experiments")
data object ExperimentCatalogRoute : UiPlaygroundNavKey {
    override val pathSegment = "experiments".toPathSegment()
}

@Composable
fun ExperimentsCatalogScreen(
    navController: NavController,
) {
    CatalogScreen(
        items = Experiment.entries.toList(),
        onItemClick = { experiment ->
            navController.navigate(ExperimentRoute(experiment))
        },
        title = "Experiments",
        onNavigateBack = navController::goBack,
        actions = {
            ConfigureButton(
                onClick = { navController.navigate(ConfigurationRoute) },
            )
        }
    )
}
