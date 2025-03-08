package com.alexrdclement.uiplayground.app.demo.control

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.alexrdclement.uiplayground.app.demo.shaders.DemoModifier
import com.alexrdclement.uiplayground.app.demo.subject.DemoSubject
import com.alexrdclement.uiplayground.app.preview.UiPlaygroundPreview
import com.alexrdclement.uiplayground.shaders.ColorSplitMode

@Preview
@Composable
private fun Preview() {
    UiPlaygroundPreview {
        Controls(
            controls = listOf(
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
