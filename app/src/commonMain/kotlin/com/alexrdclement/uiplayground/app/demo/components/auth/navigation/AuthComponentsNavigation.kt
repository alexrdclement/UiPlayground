package com.alexrdclement.uiplayground.app.demo.components.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.alexrdclement.uiplayground.app.catalog.CatalogScreen
import com.alexrdclement.uiplayground.app.configuration.ConfigureButton
import com.alexrdclement.uiplayground.app.demo.popBackStackIfResumed
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
object AuthComponentsGraphRoute

@Serializable
@SerialName("auth")
object AuthComponentCatalogRoute

fun NavGraphBuilder.authComponentsGraph(
    navController: NavController,
    onConfigureClick: () -> Unit,
) {
    navigation<AuthComponentsGraphRoute>(
        startDestination = AuthComponentCatalogRoute,
    ) {
        authComponentCatalogScreen(
            onItemClick = navController::navigateToComponent,
            onNavigateBack = navController::popBackStackIfResumed,
            onConfigureClick = onConfigureClick,
        )
        authComponentScreen(
            onNavigateBack = navController::popBackStackIfResumed,
            onConfigureClick = onConfigureClick,
        )
    }
}

fun NavController.navigateToAuthComponents() {
    this.navigate(AuthComponentsGraphRoute) {
        launchSingleTop = true
    }
}

private fun NavGraphBuilder.authComponentCatalogScreen(
    onItemClick: (AuthComponent) -> Unit,
    onNavigateBack: () -> Unit,
    onConfigureClick: () -> Unit,
) {
    composable<AuthComponentCatalogRoute> {
        CatalogScreen(
            items = AuthComponent.entries.toList(),
            onItemClick = onItemClick,
            title = "Auth",
            onNavigateBack = onNavigateBack,
            actions = {
                ConfigureButton(onClick = onConfigureClick)
            },
        )
    }
}
