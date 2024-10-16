package com.alexrdclement.uiplayground.demo.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.components.Surface
import com.alexrdclement.uiplayground.demo.components.demo.MediaControlSheetDemo

@Composable
fun ComponentScreen(
    component: Component,
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        when (component) {
            Component.MediaControlSheet -> MediaControlSheetDemo()
        }
    }
}
