package com.alexrdclement.uiplayground.demo.components

import androidx.compose.runtime.Composable
import com.alexrdclement.uiplayground.demo.components.demo.MediaControlSheetDemo

@Composable
fun ComponentScreen(
    component: Component,
) {
    when (component) {
        Component.MediaControlSheet -> MediaControlSheetDemo()
    }
}
