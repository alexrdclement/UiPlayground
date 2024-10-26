package com.alexrdclement.uiplayground.app.demo.components.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.alexrdclement.uiplayground.app.demo.components.Component
import com.alexrdclement.uiplayground.demo.components.ComponentScreen
import kotlinx.serialization.Serializable

@Serializable
data class ComponentRoute(
    // Serializable enums not supported in multiplatform navigation as of 2.8.0-alpha10
    val componentOrdinal: Int,
)

fun NavGraphBuilder.componentScreen() {
    composable<ComponentRoute> { backStackEntry ->
        val componentRoute: ComponentRoute = backStackEntry.toRoute()
        val component = Component.entries[componentRoute.componentOrdinal]
        ComponentScreen(
            component = component,
        )
    }
}

fun NavController.navigateToComponent(component: Component) {
    this.navigate(ComponentRoute(component.ordinal)) {
        launchSingleTop = true
    }
}
