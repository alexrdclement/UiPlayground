package com.alexrdclement.uiplayground.app.theme.typography.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.alexrdclement.uiplayground.app.theme.typography.TypographyScreen
import com.alexrdclement.uiplayground.theme.control.ThemeController
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("typography")
object TypographyRoute

fun NavController.navigateToTypography() {
    this.navigate(TypographyRoute) {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.typographyScreen(
    themeController: ThemeController,
    onNavigateBack: () -> Unit,
) {
    composable<TypographyRoute> {
        TypographyScreen(
            themeController = themeController,
            onNavigateBack = onNavigateBack,
        )
    }
}
