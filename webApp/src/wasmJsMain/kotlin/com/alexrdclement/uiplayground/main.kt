package com.alexrdclement.uiplayground

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import androidx.navigation.ExperimentalBrowserHistoryApi
import androidx.navigation.bindToBrowserNavigation
import com.alexrdclement.uiplayground.app.App

@OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalBrowserHistoryApi::class,
)
fun main() {
    ComposeViewport {
        App(
            onNavHostReady = { it.bindToBrowserNavigation() },
        )
    }
}
