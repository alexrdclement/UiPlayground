package com.alexrdclement.uiplayground.demo.components.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.alexrdclement.uiplayground.catalog.CatalogScreen
import com.alexrdclement.uiplayground.demo.components.Component
import com.alexrdclement.uiplayground.demo.components.ComponentScreen

private const val componentsGraphRoute = "componentGraph"
private const val componentCatalogRoute = "componentCatalog"

fun NavGraphBuilder.componentsGraph(
    navController: NavController,
) {
    navigation(
        route = componentsGraphRoute,
        startDestination = componentCatalogRoute,
    ) {
        componentCatalogScreen(
            onItemClick = navController::navigateToComponent,
        )
        componentScreen()
    }
}

fun NavController.navigateToComponents() {
    this.navigate(componentsGraphRoute) {
        launchSingleTop = true
    }
}

private fun NavGraphBuilder.componentCatalogScreen(
    onItemClick: (Component) -> Unit,
) {
    composable(componentCatalogRoute) {
        CatalogScreen(
            items = Component.entries.toList(),
            onItemClick = onItemClick,
        )
    }
}
