package com.alexrdclement.uiplayground

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.alexrdclement.uiplayground.app.App
import com.alexrdclement.uiplayground.app.catalog.navigation.MainCatalogRoute
import com.alexrdclement.uiplayground.app.navigation.rememberNavState
import com.github.terrakok.navigation3.browser.HierarchicalBrowserNavigation
import com.github.terrakok.navigation3.browser.buildBrowserHistoryFragment

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport {
        val navigationState = rememberNavState(startRoute = MainCatalogRoute)

        HierarchicalBrowserNavigation(
            currentDestination = remember { derivedStateOf { navigationState.backStack.lastOrNull() } },
            currentDestinationName = { key ->
                key?.pathSegment?.value?.let(::buildBrowserHistoryFragment)
            }
        )

        App(
            navState = navigationState,
        )
    }
}
