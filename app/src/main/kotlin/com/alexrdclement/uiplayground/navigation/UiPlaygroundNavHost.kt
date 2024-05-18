package com.alexrdclement.uiplayground.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.alexrdclement.uiplayground.catalog.MainCatalogItem
import com.alexrdclement.uiplayground.catalog.navigation.mainCatalogRoute
import com.alexrdclement.uiplayground.catalog.navigation.mainCatalogScreen
import com.alexrdclement.uiplayground.demo.components.navigation.componentsGraph
import com.alexrdclement.uiplayground.demo.components.navigation.navigateToComponents
import com.alexrdclement.uiplayground.demo.shaders.navigation.navigateToShaders
import com.alexrdclement.uiplayground.demo.shaders.navigation.shadersScreen

@Composable
fun UiPlaygroundNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = mainCatalogRoute,
    ) {
        mainCatalogScreen(
            onItemClick = { item ->
                when (item) {
                    MainCatalogItem.Components -> navController.navigateToComponents()
                    MainCatalogItem.Shaders -> navController.navigateToShaders()
                }
            }
        )
        shadersScreen()
        componentsGraph(
            navController = navController,
        )
    }
}
