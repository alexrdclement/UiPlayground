package com.alexrdclement.uiplayground.components.layout

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.components.core.IndeterminateProgressIndicator
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun IndeterminateProgressDialogContent(
    title: String = "",
    modifier: Modifier = Modifier,
) {
    DialogContent(
        title = title,
        modifier = modifier,
    ) {
        IndeterminateProgressIndicator(
            modifier = Modifier,
        )
    }
}

@Preview
@Composable
private fun IndeterminateProgressDialogContentPreview() {
    PlaygroundTheme {
        IndeterminateProgressDialogContent(
            title = "Doing something"
        )
    }
}
