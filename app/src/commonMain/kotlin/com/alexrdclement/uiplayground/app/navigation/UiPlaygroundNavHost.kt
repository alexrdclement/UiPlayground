package com.alexrdclement.uiplayground.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.alexrdclement.uiplayground.app.catalog.MainCatalogItem
import com.alexrdclement.uiplayground.app.catalog.navigation.CatalogRoute
import com.alexrdclement.uiplayground.app.catalog.navigation.mainCatalogScreen
import com.alexrdclement.uiplayground.app.configuration.ConfigurationController
import com.alexrdclement.uiplayground.app.configuration.navigation.configurationGraph
import com.alexrdclement.uiplayground.app.configuration.navigation.navigateToConfiguration
import com.alexrdclement.uiplayground.app.demo.components.componentsGraph
import com.alexrdclement.uiplayground.app.demo.components.navigateToComponents
import com.alexrdclement.uiplayground.app.demo.experiments.navigation.experimentsGraph
import com.alexrdclement.uiplayground.app.demo.experiments.navigation.navigateToExperiments
import com.alexrdclement.uiplayground.app.demo.shaders.navigation.navigateToShaders
import com.alexrdclement.uiplayground.app.demo.shaders.navigation.shadersScreen
import com.alexrdclement.uiplayground.app.theme.navigation.navigateToTheme
import com.alexrdclement.uiplayground.app.theme.navigation.themeGraph
import com.alexrdclement.uiplayground.theme.control.ThemeController

@Composable
fun UiPlaygroundNavHost(
    configurationController: ConfigurationController,
    themeController: ThemeController,
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = CatalogRoute,
    ) {
        mainCatalogScreen(
            onItemClick = { item ->
                when (item) {
                    MainCatalogItem.Components -> navController.navigateToComponents()
                    MainCatalogItem.Experiments -> navController.navigateToExperiments()
                    MainCatalogItem.Shaders -> navController.navigateToShaders()
                }
            },
            onConfigureClick = navController::navigateToConfiguration,
        )
        configurationGraph(
            configurationController = configurationController,
            onConfigureThemeClick = navController::navigateToTheme,
        )
        componentsGraph(
            navController = navController,
            onConfigureClick = navController::navigateToConfiguration,
        )
        experimentsGraph(
            navController = navController,
            onConfigureClick = navController::navigateToConfiguration,
        )
        shadersScreen(
            navController = navController,
            onConfigureClick = navController::navigateToConfiguration,
        )
        themeGraph(
            navController = navController,
            themeController = themeController,
        )
    }
}
