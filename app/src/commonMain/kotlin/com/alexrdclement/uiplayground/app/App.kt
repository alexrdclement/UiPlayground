package com.alexrdclement.uiplayground.app

import androidx.compose.runtime.Composable
import com.alexrdclement.uiplayground.app.navigation.UiPlaygroundNavHost
import com.alexrdclement.uiplayground.components.Surface
import com.alexrdclement.uiplayground.theme.PlaygroundTheme

@Composable
fun App() {
    PlaygroundTheme {
        Surface {
            UiPlaygroundNavHost()
        }
    }
}
