package com.alexrdclement.uiplayground.components.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.components.core.Button
import com.alexrdclement.uiplayground.components.core.Text
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ConfirmCancelButtonRow(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Button(
            onClick = onDismiss,
        ) {
            Text("Cancel")
        }
        Button(
            onClick = onConfirm,
            modifier = Modifier
                .padding(start = PlaygroundTheme.spacing.medium)
        ) {
            Text("OK")
        }
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
