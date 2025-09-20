package com.alexrdclement.uiplayground.app.demo.components.money.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.alexrdclement.uiplayground.app.catalog.CatalogScreen
import com.alexrdclement.uiplayground.app.configuration.ConfigureButton
import com.alexrdclement.uiplayground.app.demo.popBackStackIfResumed
import kotlinx.serialization.Serializable

@Serializable
object MoneyComponentsGraphRoute

@Serializable
object MoneyComponentCatalogRoute

fun NavGraphBuilder.moneyComponentsGraph(
    navController: NavController,
    onConfigureClick: () -> Unit,
) {
    navigation<MoneyComponentsGraphRoute>(
        startDestination = MoneyComponentCatalogRoute,
    ) {
        moneyComponentCatalogScreen(
            onItemClick = navController::navigateToMoneyComponent,
            onNavigateBack = navController::popBackStackIfResumed,
            onConfigureClick = onConfigureClick,
        )
        moneyComponentScreen(
            onNavigateBack = navController::popBackStackIfResumed,
            onConfigureClick = onConfigureClick,
        )
    }
}

fun NavController.navigateToMoneyComponents() {
    this.navigate(MoneyComponentsGraphRoute) {
        launchSingleTop = true
    }
}

private fun NavGraphBuilder.moneyComponentCatalogScreen(
    onItemClick: (MoneyComponent) -> Unit,
    onNavigateBack: () -> Unit,
    onConfigureClick: () -> Unit,
) {
    composable<MoneyComponentCatalogRoute> {
        CatalogScreen(
            items = MoneyComponent.entries.toList(),
            onItemClick = onItemClick,
            title = "Money",
            onNavigateBack = onNavigateBack,
            actions = {
                ConfigureButton(onClick = onConfigureClick)
            },
        )
    }
}
