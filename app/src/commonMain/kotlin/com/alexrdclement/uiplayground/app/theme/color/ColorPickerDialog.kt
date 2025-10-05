package com.alexrdclement.uiplayground.app.theme.color

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.alexrdclement.uiplayground.components.color.ColorPicker
import com.alexrdclement.uiplayground.components.core.Surface
import com.alexrdclement.uiplayground.components.layout.ConfirmCancelButtonRow
import com.alexrdclement.uiplayground.theme.ColorToken
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import com.alexrdclement.uiplayground.theme.toColor
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ColorPickerDialog(
    colorToken: ColorToken,
    onColorSelected: (Color) -> Unit,
    onDismissRequest: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Surface(
            border = BorderStroke(1.dp, PlaygroundTheme.colorScheme.outline),
        ) {
            ColorPickerDialogContent(
                colorToken = colorToken,
                onColorSelected = onColorSelected,
                onDismissRequest = onDismissRequest,
                modifier = Modifier
                    .padding(PlaygroundTheme.spacing.medium)
            )
        }
    }
}

@Composable
fun ColorPickerDialogContent(
    colorToken: ColorToken,
    onColorSelected: (Color) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val initialColor = colorToken.toColor()
    var color by remember { mutableStateOf(initialColor) }

    Column(
        verticalArrangement = Arrangement.spacedBy(PlaygroundTheme.spacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(PlaygroundTheme.spacing.large)
    ) {
        ColorPicker(
            color = color,
            onColorChange = { color = it },
            modifier = Modifier
                .weight(1f, fill = false)
        )
        ConfirmCancelButtonRow(
            onConfirm = {
                onColorSelected(color)
                onDismissRequest()
            },
            onDismiss = onDismissRequest,
            modifier = Modifier
                .padding(top = PlaygroundTheme.spacing.large)
        )
    }
}

@Preview
@Composable
fun ColorPickerDialogPreview() {
    PlaygroundTheme {
        ColorPickerDialogContent(
            colorToken = ColorToken.Primary,
            onColorSelected = {},
            onDismissRequest = {}
        )
    }
}
