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
            width = 500.dp,
            height = 1000.dp,
            position = WindowPosition.Aligned(Alignment.BottomEnd),
        ),
        title = "UiPlayground",
        alwaysOnTop = true,
    ) {
        App()
    }
}
