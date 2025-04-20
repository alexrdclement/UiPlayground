package com.alexrdclement.uiplayground

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.alexrdclement.uiplayground.app.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = rememberWindowState(
            width = 1200.dp,
            height = 960.dp,
            position = WindowPosition.Aligned(Alignment.Center),
        ),
        title = "UiPlayground",
    ) {
        App()
    }
}
