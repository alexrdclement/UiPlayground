package com.alexrdclement.uiplayground.demo.subject

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.alexrdclement.uiplayground.ui.preview.UiPlaygroundPreview

@Composable
fun DemoText(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.displayLarge,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Text(
            text = "Hello world",
            fontSize = textStyle.fontSize,
            fontStyle = textStyle.fontStyle,
            fontWeight = textStyle.fontWeight,
            fontFamily = textStyle.fontFamily,
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
