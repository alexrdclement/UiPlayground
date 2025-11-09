package com.alexrdclement.uiplayground.components.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun IndeterminateProgressIndicator(
    modifier: Modifier = Modifier,
) {
    Text("...", modifier = modifier)
}

@Preview
@Composable
fun PreviewIndeterminateProgressIndicator() {
    PlaygroundTheme {
        IndeterminateProgressIndicator()
    }
}
