package com.alexrdclement.uiplayground.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.alexrdclement.uiplayground.app.catalog.navigation.mainCatalogScreen
import com.alexrdclement.uiplayground.app.demo.components.navigation.componentsGraph
import com.alexrdclement.uiplayground.app.demo.components.navigation.navigateToComponents
import com.alexrdclement.uiplayground.app.demo.experiments.navigation.experimentsGraph
import com.alexrdclement.uiplayground.app.demo.experiments.navigation.navigateToExperiments
import com.alexrdclement.uiplayground.app.demo.shaders.navigation.navigateToShaders
import com.alexrdclement.uiplayground.app.demo.shaders.navigation.shadersScreen

@Composable
fun UiPlaygroundNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = com.alexrdclement.uiplayground.app.catalog.navigation.mainCatalogRoute,
    ) {
        mainCatalogScreen(
            onItemClick = { item ->
                when (item) {
                    com.alexrdclement.uiplayground.app.catalog.MainCatalogItem.Components -> navController.navigateToComponents()
                    com.alexrdclement.uiplayground.app.catalog.MainCatalogItem.Experiments -> navController.navigateToExperiments()
                    com.alexrdclement.uiplayground.app.catalog.MainCatalogItem.Shaders -> navController.navigateToShaders()
                }
            }
        )
        componentsGraph(
            navController = navController,
        )
        experimentsGraph(
            navController = navController,
        )
        shadersScreen()
    }
}
