package com.alexrdclement.uiplayground.app.demo.components.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.alexrdclement.uiplayground.app.catalog.CatalogScreen
import com.alexrdclement.uiplayground.app.demo.components.Component
import kotlinx.serialization.Serializable

@Serializable
object ComponentsGraphRoute

@Serializable
object ComponentCatalogRoute

fun NavGraphBuilder.componentsGraph(
    navController: NavController,
) {
    navigation<ComponentsGraphRoute>(
        startDestination = ComponentCatalogRoute,
    ) {
        componentCatalogScreen(
            onItemClick = navController::navigateToComponent,
        )
        componentScreen()
    }
}

fun NavController.navigateToComponents() {
    this.navigate(ComponentsGraphRoute) {
        launchSingleTop = true
    }
}

private fun NavGraphBuilder.componentCatalogScreen(
    onItemClick: (Component) -> Unit,
) {
    composable<ComponentCatalogRoute> {
        CatalogScreen(
            items = Component.entries.toList(),
            onItemClick = onItemClick,
        )
    }
}
