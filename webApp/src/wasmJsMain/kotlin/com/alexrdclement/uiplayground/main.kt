package com.alexrdclement.uiplayground

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.alexrdclement.uiplayground.app.App
import com.alexrdclement.uiplayground.app.catalog.navigation.MainCatalogRoute
import com.alexrdclement.uiplayground.app.navigation.deeplink.fromDeeplinkPath
import com.alexrdclement.uiplayground.app.navigation.deeplink.toDeeplinkPath
import com.alexrdclement.uiplayground.app.navigation.UiPlaygroundNavKey
import com.alexrdclement.uiplayground.app.navigation.rememberNavState
import com.github.terrakok.navigation3.browser.ChronologicalBrowserNavigation

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport {
        val navState = rememberNavState(startRoute = MainCatalogRoute)

        ChronologicalBrowserNavigation(
            backStack = navState.backStack,
            saveKey = { key ->
                "#${key.toDeeplinkPath()}"
            },
            restoreKey = { fragment ->
                UiPlaygroundNavKey.fromDeeplinkPath(fragment.removePrefix("#"))
            }
        )

        App(
            navState = navState,
        )
    }
}
