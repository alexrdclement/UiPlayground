package com.alexrdclement.uiplayground.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.app.navigation.UiPlaygroundNavHost
import com.alexrdclement.uiplayground.components.Surface
import com.alexrdclement.uiplayground.theme.PlaygroundTheme

@Composable
fun App(modifier: Modifier = Modifier) {
    PlaygroundTheme {
        Surface {
            UiPlaygroundNavHost()
        }
    }
}
