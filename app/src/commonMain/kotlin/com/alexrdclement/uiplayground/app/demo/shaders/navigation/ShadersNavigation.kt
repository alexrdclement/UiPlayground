package com.alexrdclement.uiplayground.app.demo.shaders.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.alexrdclement.uiplayground.app.demo.popBackStackIfResumed
import com.alexrdclement.uiplayground.app.demo.shaders.ShaderScreen
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("shaders")
object ShadersRoute

fun NavGraphBuilder.shadersScreen(
    navController: NavController,
    onConfigureClick: () -> Unit,
) {
    composable<ShadersRoute> {
        ShaderScreen(
            onNavigateBack = navController::popBackStackIfResumed,
            onConfigureClick = onConfigureClick,
        )
    }
}

fun NavController.navigateToShaders() {
    this.navigate(ShadersRoute) {
        launchSingleTop = true
    }
}
