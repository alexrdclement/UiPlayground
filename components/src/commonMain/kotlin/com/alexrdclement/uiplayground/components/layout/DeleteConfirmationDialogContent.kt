package com.alexrdclement.uiplayground.components.layout

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DeleteConfirmationDialogContent(
    contentTitle: String,
    onConfirm: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DialogContent(
        title = "Delete \"$contentTitle\"?",
        message = "This action cannot be undone.",
        onConfirm = onConfirm,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
    )
}

@Preview
@Composable
fun DeleteConfirmationDialogContentPreview() {
    PlaygroundTheme {
        DeleteConfirmationDialogContent(
            contentTitle = "Log entry",
            onConfirm = {},
            onDismissRequest = {},
        )
    }
}
