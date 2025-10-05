package com.alexrdclement.uiplayground.app.theme.shape.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.alexrdclement.uiplayground.app.theme.shape.ShapeScreen
import com.alexrdclement.uiplayground.theme.control.ThemeController
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("shape")
object ShapeRoute

fun NavController.navigateToShape() {
    this.navigate(ShapeRoute) {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.shapeScreen(
    themeController: ThemeController,
    onNavigateBack: () -> Unit,
) {
    composable<ShapeRoute> {
        ShapeScreen(
            themeController = themeController,
            onNavigateBack = onNavigateBack,
        )
    }
}
