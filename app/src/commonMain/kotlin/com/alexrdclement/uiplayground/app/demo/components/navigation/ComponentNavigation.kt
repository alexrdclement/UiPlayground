package com.alexrdclement.uiplayground.app.demo.components.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.alexrdclement.uiplayground.app.demo.components.Component
import com.alexrdclement.uiplayground.demo.components.ComponentScreen
import kotlinx.serialization.Serializable

@Serializable
data class ComponentRoute(val component: Component)

fun NavGraphBuilder.componentScreen() {
    composable<ComponentRoute> { backStackEntry ->
        val componentRoute: ComponentRoute = backStackEntry.toRoute()
        ComponentScreen(
            component = componentRoute.component,
        )
    }
}

fun NavController.navigateToComponent(component: Component) {
    this.navigate(ComponentRoute(component)) {
        launchSingleTop = true
    }
}
