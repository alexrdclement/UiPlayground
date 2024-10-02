package com.alexrdclement.uiplayground.demo.experiments.demo.textfield

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.alexrdclement.uiplayground.components.Text
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import com.alexrdclement.uiplayground.ui.preview.UiPlaygroundPreview

@Composable
fun TextFieldDemo(
    textFieldState: TextFieldState = rememberTextFieldState(),
) {
    val text by snapshotFlow { textFieldState.text.toString() }
        .collectAsState(initial = textFieldState.text.toString())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = text,
            style = PlaygroundTheme.typography.headline,
        )
        CurrencyAmountField(
            textFieldState = textFieldState,
        )
    }
}

@Preview
@Composable
private fun Preview() {
    UiPlaygroundPreview {
        val textFieldState = rememberTextFieldState(initialText = "123.45")
        TextFieldDemo(textFieldState = textFieldState)
    }
}
