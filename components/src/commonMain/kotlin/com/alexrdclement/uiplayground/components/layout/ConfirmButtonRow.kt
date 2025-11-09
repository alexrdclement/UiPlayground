package com.alexrdclement.uiplayground.components.layout

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ConfirmButtonRow(
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DialogContentSingleButtonRow(
        modifier = modifier,
    ) {
        ConfirmButton(
            onConfirm = onConfirm,
        )
    }
}

@Preview
@Composable
private fun ConfirmButtonRow() {
    PlaygroundTheme {
        ConfirmButtonRow(
            onConfirm = {},
        )
    }
}
