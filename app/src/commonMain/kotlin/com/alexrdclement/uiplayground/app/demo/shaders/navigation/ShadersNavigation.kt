package com.alexrdclement.uiplayground.app.demo.shaders.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.alexrdclement.uiplayground.app.demo.shaders.ShaderScreen
import kotlinx.serialization.Serializable

@Serializable
object ShadersRoute

fun NavGraphBuilder.shadersScreen() {
    composable<ShadersRoute> {
        ShaderScreen()
    }
}

fun NavController.navigateToShaders() {
    this.navigate(ShadersRoute) {
        launchSingleTop = true
    }
}
