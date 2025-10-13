package com.alexrdclement.uiplayground.components.layout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.components.core.Surface
import com.alexrdclement.uiplayground.components.core.Text
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DialogContent(
    title: String,
    message: String,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    onConfirm: (() -> Unit)? = null,
) {
    DialogContent(
        title = title,
        message = message,
        buttonRow = { onDismissRequest, modifier ->
            onConfirm?.let {
                ConfirmCancelButtonRow(
                    onConfirm = it,
                    onDismiss = onDismissRequest,
                    modifier = modifier,
                )
            } ?: run {
                ConfirmButtonRow(
                    onConfirm = onDismissRequest,
                    modifier = modifier,
                )
            }
        },
        onDismissRequest = onDismissRequest,
        modifier = modifier,
    )
}

@Composable
fun ErrorDialogContent(
    title: String = "Error",
    message: String,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DialogContent(
        title = title,
        message = message,
        buttonRow = { onDismissRequest, modifier ->
            ConfirmButtonRow(
                onConfirm = onDismissRequest,
                modifier = modifier,
            )
        },
        onDismissRequest = onDismissRequest,
        modifier = modifier,
    )
}

@Composable
fun DialogContent(
    title: String,
    message: String,
    onDismissRequest: () -> Unit,
    buttonRow: @Composable (onDismissRequest: () -> Unit, modifier: Modifier) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        border = BorderStroke(1.dp, PlaygroundTheme.colorScheme.outline),
        modifier = modifier,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(PlaygroundTheme.spacing.medium),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(PlaygroundTheme.spacing.large)
        ) {
            Text(
                text = title,
                style = PlaygroundTheme.typography.titleLarge.merge(
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier
                    .padding(bottom = PlaygroundTheme.spacing.medium)
            )
            Text(
                text = message,
                style = PlaygroundTheme.typography.bodyLarge.merge(
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier
                    .padding(bottom = PlaygroundTheme.spacing.large)
            )
            buttonRow(onDismissRequest, Modifier.align(Alignment.End))
        }
    }
}

@Preview
@Composable
private fun DialogContentPreview() {
    PlaygroundTheme {
        DialogContent(
            title = "Title",
            message = "Long message to show in the dialog content area.",
            onDismissRequest = {},
            onConfirm = {},
            modifier = Modifier
                .padding(PlaygroundTheme.spacing.medium)
        )
    }
}

@Preview
@Composable
private fun ErrorDialogContentPreview() {
    PlaygroundTheme {
        ErrorDialogContent(
            message = "An error occurred while processing your request.",
            onDismissRequest = {},
            modifier = Modifier
                .padding(PlaygroundTheme.spacing.medium)
        )
    }
}
