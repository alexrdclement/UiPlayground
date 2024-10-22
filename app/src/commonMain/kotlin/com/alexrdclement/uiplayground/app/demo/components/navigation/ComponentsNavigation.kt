package com.alexrdclement.uiplayground.app.demo.components.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.alexrdclement.uiplayground.app.catalog.CatalogScreen
import com.alexrdclement.uiplayground.app.demo.components.Component

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
