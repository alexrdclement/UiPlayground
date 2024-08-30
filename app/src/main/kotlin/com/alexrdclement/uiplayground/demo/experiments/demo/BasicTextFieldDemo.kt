package com.alexrdclement.uiplayground.demo.experiments.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.byValue
import androidx.compose.foundation.text.input.insert
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.ui.preview.UiPlaygroundPreview
import java.text.DecimalFormatSymbols

@Composable
fun BasicTextFieldDemo(
    textFieldState: TextFieldState = rememberTextFieldState(),
    prefix: String = "$",
    placeholder: String = "0",
    groupingSeparator: String = DecimalFormatSymbols.getInstance().groupingSeparator.toString(),
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
        BasicText(
            text = text,
            style = MaterialTheme.typography.headlineLarge.copy(
                color = MaterialTheme.colorScheme.onSurface,
            ),
        )

        BasicTextField(
            state = textFieldState,
            textStyle = MaterialTheme.typography.headlineLarge.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            ),
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(8.dp),
            inputTransformation = InputTransformation.byValue { _, proposed ->
                proposed.filter { it.isDigit() }
            },
            outputTransformation = {
                for (index in 1 until originalText.length) {
                    if (index % 3 == 0) {
                        insert(originalText.length - index, groupingSeparator)
                    }
                }
            },
            lineLimits = TextFieldLineLimits.SingleLine,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
            ),
            decorator = { textField ->
                Row(
                    modifier = Modifier
                        .border(2.dp, Color.Red),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    BasicText(
                        prefix,
                        style = MaterialTheme.typography.headlineLarge.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    )

                    Box {
                        if (textFieldState.text.isEmpty()) {
                            // Placeholder
                            BasicText(
                                placeholder,
                                style = MaterialTheme.typography.headlineLarge.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                        alpha = 0.5f,
                                    ),
                                )
                            )
                        }

                        // Min width to ensure cursor still appears
                        Box(modifier = Modifier.widthIn(min = 8.dp)) {
                            textField()
                        }
                    }
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