package com.alexrdclement.uiplayground.components.layout

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ConfirmCancelButtonRow(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DialogContentButtonRow(
        modifier = modifier,
    ) {
        CancelButton(
            onDismiss = onDismiss,
        )
        ConfirmButton(
            onConfirm = onConfirm,
            modifier = Modifier
                .padding(start = PlaygroundTheme.spacing.medium)
        )
    }
}

@Preview
@Composable
fun ConfirmCancelButtonRowPreview() {
    PlaygroundTheme {
        ConfirmCancelButtonRow(
            onConfirm = {},
            onDismiss = {},
        )
    }
}
