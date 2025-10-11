package com.alexrdclement.uiplayground.app.theme.styles.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.alexrdclement.uiplayground.app.catalog.CatalogScreen
import com.alexrdclement.uiplayground.app.theme.styles.Styles
import com.alexrdclement.uiplayground.theme.control.ThemeController
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
object StylesGraphRoute

@Serializable
@SerialName("styles")
object StylesCatalogRoute

fun NavGraphBuilder.stylesGraph(
    navController: NavController,
    themeController: ThemeController,
    onNavigateBack: () -> Unit,
) {
    navigation<StylesGraphRoute>(
        startDestination = StylesCatalogRoute,
    ) {
        stylesScreen(
            onItemClick = { style ->
                when (style) {
                    Styles.Button -> navController.navigateToButtonStyles()
                }
            },
            onNavigateBack = onNavigateBack,
        )
        buttonStylesScreen(
            themeController = themeController,
            onNavigateBack = onNavigateBack,
        )
    }
}

fun NavController.navigateToStyles() {
    this.navigate(StylesGraphRoute) {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.stylesScreen(
    onItemClick: (Styles) -> Unit,
    onNavigateBack: () -> Unit,
) {
    composable<StylesCatalogRoute> {
        CatalogScreen(
            items = Styles.entries.toList(),
            onItemClick = onItemClick,
            title = "Styles",
            onNavigateBack = onNavigateBack,
        )
    }
}
