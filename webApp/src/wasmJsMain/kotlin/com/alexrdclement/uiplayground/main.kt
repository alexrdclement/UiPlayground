package com.alexrdclement.uiplayground

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.embarrasdf.palette.navigation.NavKey
import com.embarrasdf.palette.navigation.fromDeeplink
import com.embarrasdf.palette.navigation.toDeeplink
import com.alexrdclement.uiplayground.app.App
import com.alexrdclement.uiplayground.app.navigation.rememberUiPlaygroundNavController
import com.github.terrakok.navigation3.browser.ChronologicalBrowserNavigation

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport {
        val navController = rememberUiPlaygroundNavController()

        ChronologicalBrowserNavigation(
            backStack = navController.state.backStack,
            saveKey = { key -> "#${key.toDeeplink(tree = navController.state.navGraph)}" },
            restoreKey = { fragment ->
                NavKey.fromDeeplink(
                    deeplink = fragment.removePrefix("#"),
                    navGraph = navController.state.navGraph,
                ) as? NavKey
            },
        )

        App(navController = navController)
    }
}
