package com.alexrdclement.uiplayground.app.demo.control

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.alexrdclement.uiplayground.app.preview.UiPlaygroundPreview
import kotlinx.collections.immutable.persistentListOf

@Preview
@Composable
private fun Preview() {
    UiPlaygroundPreview {
        Controls(
            controls = persistentListOf(
                Control.Slider(
                    name = "Amount",
                    value = 0.5f,
                    onValueChange = {},
                ),
                Control.Slider(
                    name = "Amount 2",
                    value = 0.5f,
                    onValueChange = {},
                )
            ),
        )
    }
}
