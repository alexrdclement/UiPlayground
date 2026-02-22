package com.alexrdclement.uiplayground.app.demo.experiments.navigation

import androidx.navigation3.runtime.EntryProviderScope
import com.alexrdclement.palette.navigation.NavController
import com.alexrdclement.palette.navigation.NavGraphBuilder
import com.alexrdclement.palette.navigation.NavKey
import com.alexrdclement.uiplayground.app.catalog.navigation.catalogEntry
import com.alexrdclement.uiplayground.app.configuration.ConfigureButton
import com.alexrdclement.uiplayground.app.configuration.navigation.ConfigurationRoute
import com.alexrdclement.uiplayground.app.demo.experiments.ExperimentScreen

fun NavGraphBuilder.experimentsNavGraph() = navGraph(
    root = ExperimentsGraph,
    start = ExperimentCatalogRoute,
) {
    route(ExperimentCatalogRoute)
    wildcardRoute<ExperimentRoute> { pathSegment ->
        ExperimentRoute(pathSegment)
    }
}

fun EntryProviderScope<NavKey>.experimentsEntryProvider(
    navController: NavController,
) {
    catalogEntry<ExperimentCatalogRoute, Experiment>(
        onItemClick = { experiment ->
            navController.navigate(ExperimentRoute(experiment))
        },
        title = "Experiments",
        onNavigateUp = navController::goBack,
        actions = {
            ConfigureButton(
                onClick = { navController.navigate(ConfigurationRoute) },
            )
        },
    )

    entry<ExperimentRoute> { route ->
        ExperimentScreen(
            experiment = route.experiment,
            onNavigateUp = navController::goBack,
            onConfigureClick = { navController.navigate(ConfigurationRoute) },
        )
    }
}
