package com.alexrdclement.uiplayground.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastMap

@Composable
fun Scaffold(
    topBar: @Composable () -> Unit = {},
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit,
) {
    Surface(modifier = modifier) {
        // Layout top bar on top of content to center content whether or not top bar exists.
        // Pass constraints to content with top bar height removed to allow to respect or ignore.
        SubcomposeLayout { constraints ->
            val topBarPlaceables = subcompose(ScaffoldComponents.TopBar, topBar).fastMap {
                it.measure(constraints)
            }

            val contentPadding = PaddingValues(top = topBarPlaceables.maxOf { it.height }.toDp())
            val contentPlaceables = subcompose(ScaffoldComponents.Content) {
                content(contentPadding)
            }.fastMap { it.measure(constraints) }

            layout(constraints.maxWidth, constraints.maxHeight) {
                contentPlaceables.fastForEach { it.place(0, 0) }
                topBarPlaceables.fastForEach { it.place(0, 0) }
            }
        }
    }
}

private enum class ScaffoldComponents {
    TopBar,
    Content,
}
