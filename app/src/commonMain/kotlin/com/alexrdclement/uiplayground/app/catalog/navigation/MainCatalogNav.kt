package com.alexrdclement.uiplayground.app.catalog.navigation

import androidx.compose.runtime.Composable
import com.alexrdclement.uiplayground.app.navigation.NavController
import com.alexrdclement.uiplayground.app.navigation.UiPlaygroundNavKey


@Composable
fun MainNav(
    route: UiPlaygroundNavKey,
    navController: NavController,
) {
    when (route) {
        is MainCatalogRoute -> MainCatalogScreen(
            navController = navController,
        )
    }
}
