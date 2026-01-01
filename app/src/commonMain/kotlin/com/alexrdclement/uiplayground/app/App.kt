package com.alexrdclement.uiplayground.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.alexrdclement.palette.components.core.Surface
import com.alexrdclement.palette.theme.PaletteTheme
import com.alexrdclement.uiplayground.app.configuration.rememberConfigurationController
import com.alexrdclement.uiplayground.app.navigation.UiPlaygroundNavHost

@Composable
fun App(
    onNavHostReady: suspend (NavController) -> Unit = {}
) {
    val navController = rememberNavController()
    val configurationController = rememberConfigurationController()

    LaunchedEffect(navController) {
        onNavHostReady(navController)
    }

    PaletteTheme {
        Surface {
            UiPlaygroundNavHost(
                navController = navController,
                configurationController = configurationController,
            )
        }
    }
}
