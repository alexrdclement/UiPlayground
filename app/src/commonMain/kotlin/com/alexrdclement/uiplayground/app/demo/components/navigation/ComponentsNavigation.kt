package com.alexrdclement.uiplayground.app.demo.components.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.alexrdclement.uiplayground.app.catalog.CatalogScreen
import com.alexrdclement.uiplayground.app.configuration.ConfigureButton
import com.alexrdclement.uiplayground.app.demo.components.Component
import com.alexrdclement.uiplayground.app.demo.popBackStackIfResumed
import kotlinx.serialization.Serializable

@Serializable
object ComponentsGraphRoute

@Serializable
object ComponentCatalogRoute

fun NavGraphBuilder.componentsGraph(
    navController: NavController,
    onConfigureClick: () -> Unit,
) {
    navigation<ComponentsGraphRoute>(
        startDestination = ComponentCatalogRoute,
    ) {
        componentCatalogScreen(
            onItemClick = navController::navigateToComponent,
            onNavigateBack = navController::popBackStackIfResumed,
            onConfigureClick = onConfigureClick,
        )
        componentScreen(
            onNavigateBack = navController::popBackStackIfResumed,
            onConfigureClick = onConfigureClick,
        )
    }
}

fun NavController.navigateToComponents() {
    this.navigate(ComponentsGraphRoute) {
        launchSingleTop = true
    }
}

private fun NavGraphBuilder.componentCatalogScreen(
    onItemClick: (Component) -> Unit,
    onNavigateBack: () -> Unit,
    onConfigureClick: () -> Unit,
) {
    composable<ComponentCatalogRoute> {
        CatalogScreen(
            items = Component.entries.toList(),
            onItemClick = onItemClick,
            title = "Components",
            onNavigateBack = onNavigateBack,
            actions = {
                ConfigureButton(onClick = onConfigureClick)
            },
        )
    }
}
