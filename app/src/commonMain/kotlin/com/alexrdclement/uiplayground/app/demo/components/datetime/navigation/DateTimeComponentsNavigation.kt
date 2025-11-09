package com.alexrdclement.uiplayground.app.demo.components.datetime.navigation

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
object DateTimeComponentsGraphRoute

@Serializable
@SerialName("dateTime")
object DateTimeComponentCatalogRoute

fun NavGraphBuilder.dateTimeComponentsGraph(
    navController: NavController,
    onConfigureClick: () -> Unit,
) {
    navigation<DateTimeComponentsGraphRoute>(
        startDestination = DateTimeComponentCatalogRoute,
    ) {
        dateTimeComponentCatalogScreen(
            onItemClick = navController::navigateToComponent,
            onNavigateBack = navController::popBackStackIfResumed,
            onConfigureClick = onConfigureClick,
        )
        dateTimeComponentScreen(
            onNavigateBack = navController::popBackStackIfResumed,
            onConfigureClick = onConfigureClick,
        )
    }
}

fun NavController.navigateToDateTimeComponents() {
    this.navigate(DateTimeComponentsGraphRoute) {
        launchSingleTop = true
    }
}

private fun NavGraphBuilder.dateTimeComponentCatalogScreen(
    onItemClick: (DateTimeComponent) -> Unit,
    onNavigateBack: () -> Unit,
    onConfigureClick: () -> Unit,
) {
    composable<DateTimeComponentCatalogRoute> {
        CatalogScreen(
            items = DateTimeComponent.entries.toList(),
            onItemClick = onItemClick,
            title = "DateTime",
            onNavigateBack = onNavigateBack,
            actions = {
                ConfigureButton(onClick = onConfigureClick)
            },
        )
    }
}
