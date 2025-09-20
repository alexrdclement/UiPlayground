package com.alexrdclement.uiplayground.app.demo.components.geometry.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.alexrdclement.uiplayground.app.demo.components.geometry.GeometryComponentScreen
import kotlinx.serialization.Serializable

@Serializable
data class ComponentRoute(
    // Serializable enums not supported in multiplatform navigation as of 2.8.0-alpha10
    val componentOrdinal: Int,
)

fun NavGraphBuilder.componentScreen(
    onNavigateBack: () -> Unit,
    onConfigureClick: () -> Unit,
) {
    composable<ComponentRoute> { backStackEntry ->
        val componentRoute: ComponentRoute = backStackEntry.toRoute()
        val component = GeometryComponent.entries[componentRoute.componentOrdinal]
        GeometryComponentScreen(
            component = component,
            onNavigateBack = onNavigateBack,
            onConfigureClick = onConfigureClick,
        )
    }
}

fun NavController.navigateToComponent(component: GeometryComponent) {
    this.navigate(ComponentRoute(component.ordinal)) {
        launchSingleTop = true
    }
}
