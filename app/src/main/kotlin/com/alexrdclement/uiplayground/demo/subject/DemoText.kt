package com.alexrdclement.uiplayground.demo.subject

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.alexrdclement.uiplayground.components.Text
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import com.alexrdclement.uiplayground.ui.preview.UiPlaygroundPreview

@Composable
fun DemoText(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = PlaygroundTheme.typography.display,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(PlaygroundTheme.colorScheme.surface)
    ) {
        Text(
            text = "Hello world",
            style = textStyle,
            modifier = modifier.align(Alignment.Center)
        )
    }
}

@Preview
@Composable
private fun Preview() {
    UiPlaygroundPreview {
        DemoText()
    }
}
