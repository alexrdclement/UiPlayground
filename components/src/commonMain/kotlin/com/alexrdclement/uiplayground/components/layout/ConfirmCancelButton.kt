package com.alexrdclement.uiplayground.components.layout

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.components.core.Button
import com.alexrdclement.uiplayground.components.core.Text
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import com.alexrdclement.uiplayground.theme.styles.ButtonStyleToken
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ConfirmButton(
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        style = ButtonStyleToken.Secondary,
        onClick = onConfirm,
        modifier = modifier,
    ) {
        Text("OK")
    }
}

@Composable
fun CancelButton(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        style = ButtonStyleToken.Secondary,
        onClick = onDismiss,
        modifier = modifier,
    ) {
        Text("Cancel")
    }
}

@Preview
@Composable
private fun ConfirmButtonPreview() {
    PlaygroundTheme {
        ConfirmButton(
            onConfirm = {},
        )
    }
}

@Preview
@Composable
private fun CancelButtonPreview() {
    PlaygroundTheme {
        CancelButton(
            onDismiss = {},
        )
    }
}
