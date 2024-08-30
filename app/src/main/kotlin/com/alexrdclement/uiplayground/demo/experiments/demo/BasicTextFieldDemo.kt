package com.alexrdclement.uiplayground.demo.experiments.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.ui.preview.UiPlaygroundPreview

@Composable
fun BasicTextFieldDemo() {
    val textFieldState = rememberTextFieldState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        contentAlignment = Alignment.Center,
    ) {
        BasicTextField(
            state = textFieldState,
            textStyle = MaterialTheme.typography.headlineLarge.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            ),
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(8.dp),
            lineLimits = TextFieldLineLimits.SingleLine,
            decorator = { textField ->
                Box(
                    modifier = Modifier
                        .border(2.dp, Color.Red),
                    contentAlignment = Alignment.Center,
                ) {
                    textField()
                }
            }
        )
    }
}

@Preview
@Composable
private fun Preview() {
    UiPlaygroundPreview {
        BasicTextFieldDemo()
    }
}
