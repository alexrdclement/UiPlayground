package com.alexrdclement.uiplayground.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.alexrdclement.uiplayground.theme.PlaygroundTheme

@Preview
@Composable
private fun Preview() {
    PlaygroundTheme {
        Surface {
            val state = remember { SliderState(value = 0.5f) }
            Slider(state = state)
        }
    }
}
