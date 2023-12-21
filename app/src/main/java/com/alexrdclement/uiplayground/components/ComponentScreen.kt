package com.alexrdclement.uiplayground.components

import androidx.compose.runtime.Composable
import com.alexrdclement.uiplayground.components.demo.MediaControlSheetDemo

@Composable
fun ComponentScreen(
    component: Component,
) {
    when (component) {
        Component.MediaControlSheet -> MediaControlSheetDemo()
    }
}
