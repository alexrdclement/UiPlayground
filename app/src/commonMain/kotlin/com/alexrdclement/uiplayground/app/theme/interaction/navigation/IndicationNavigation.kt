package com.alexrdclement.uiplayground.app.theme.interaction.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.alexrdclement.uiplayground.app.catalog.CatalogScreen
import com.alexrdclement.uiplayground.app.theme.interaction.IndicationScreen
import com.alexrdclement.uiplayground.app.theme.interaction.Interaction
import com.alexrdclement.uiplayground.theme.control.ThemeController
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("indication")
object IndicationRoute

fun NavController.navigateToIndication() {
    this.navigate(IndicationRoute) {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.indicationScreen(
    themeController: ThemeController,
    onNavigateBack: () -> Unit,
) {
    composable<IndicationRoute> {
        IndicationScreen(
            themeController = themeController,
            onNavigateBack = onNavigateBack,
        )
    }
}
