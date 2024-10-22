package com.alexrdclement.uiplayground.app.demo.experiments.demo.textfield

import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.alexrdclement.uiplayground.app.preview.UiPlaygroundPreview

@Preview
@Composable
private fun Preview() {
    UiPlaygroundPreview {
        val textFieldState = rememberTextFieldState(initialText = "123.45")
        TextFieldDemo(textFieldState = textFieldState)
    }
}
