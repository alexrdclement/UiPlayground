package com.alexrdclement.uiplayground.app.main.navigation

import androidx.navigation3.runtime.EntryProviderScope
import com.embarrasdf.palette.navigation.NavController
import com.embarrasdf.palette.navigation.NavGraphBuilder
import com.embarrasdf.palette.navigation.NavKey
import com.alexrdclement.uiplayground.app.catalog.navigation.catalogEntry
import com.alexrdclement.uiplayground.app.configuration.ConfigureButton
import com.alexrdclement.uiplayground.app.configuration.navigation.ConfigurationRoute
import com.alexrdclement.uiplayground.app.demo.experiments.navigation.ExperimentsGraph
import com.alexrdclement.uiplayground.app.main.MainCatalogItem

fun NavGraphBuilder.mainNavGraph() = navGraph(
    root = MainGraph,
    start = MainCatalogRoute,
) {
    route(MainCatalogRoute)
}

fun EntryProviderScope<NavKey>.mainEntryProvider(
    navController: NavController,
) {
    catalogEntry<MainCatalogRoute, MainCatalogItem>(
        onItemClick = { item ->
            when (item) {
                MainCatalogItem.Experiments -> navController.navigate(ExperimentsGraph)
            }
        },
        actions = {
            ConfigureButton(
                onClick = { navController.navigate(ConfigurationRoute) },
            )
        },
    )
}
