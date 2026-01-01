package com.alexrdclement.uiplayground.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.alexrdclement.uiplayground.app.catalog.MainCatalogItem
import com.alexrdclement.uiplayground.app.catalog.navigation.CatalogRoute
import com.alexrdclement.uiplayground.app.catalog.navigation.mainCatalogScreen
import com.alexrdclement.uiplayground.app.configuration.ConfigurationController
import com.alexrdclement.uiplayground.app.configuration.navigation.configurationGraph
import com.alexrdclement.uiplayground.app.configuration.navigation.navigateToConfiguration
import com.alexrdclement.uiplayground.app.demo.experiments.navigation.experimentsGraph
import com.alexrdclement.uiplayground.app.demo.experiments.navigation.navigateToExperiments

@Composable
fun UiPlaygroundNavHost(
    configurationController: ConfigurationController,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = CatalogRoute,
    ) {
        mainCatalogScreen(
            onItemClick = { item ->
                when (item) {
                    MainCatalogItem.Experiments -> navController.navigateToExperiments()
                }
            },
            onConfigureClick = navController::navigateToConfiguration,
        )
        configurationGraph(
            configurationController = configurationController,
        )
        experimentsGraph(
            navController = navController,
            onConfigureClick = navController::navigateToConfiguration,
        )
    }
}
