package com.alexrdclement.uiplayground.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.alexrdclement.uiplayground.app.configuration.rememberConfigurationController
import com.alexrdclement.uiplayground.app.navigation.UiPlaygroundNavHost
import com.alexrdclement.uiplayground.components.core.Surface
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import com.alexrdclement.uiplayground.theme.control.rememberThemeController

@Composable
fun App(
    onNavHostReady: suspend (NavController) -> Unit = {}
) {
    val navController = rememberNavController()
    val configurationController = rememberConfigurationController()
    val themeController = rememberThemeController()

    LaunchedEffect(navController) {
        onNavHostReady(navController)
    }

    PlaygroundTheme(
        typography = themeController.typography,
        indication = themeController.indication,
    ) {
        Surface {
            UiPlaygroundNavHost(
                navController = navController,
                configurationController = configurationController,
                themeController = themeController,
            )
        }
    }
}
