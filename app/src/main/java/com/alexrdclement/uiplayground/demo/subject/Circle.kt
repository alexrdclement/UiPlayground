package com.alexrdclement.uiplayground.demo.subject

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.alexrdclement.uiplayground.util.UiPlaygroundPreview

@Composable
fun DemoCircle(modifier: Modifier = Modifier) {
    val color = MaterialTheme.colorScheme.primary
    Canvas(
        modifier = modifier.fillMaxSize()
    ) {
        drawCircle(color, radius = size.minDimension / 4f)
    }
}

@Preview
@Composable
private fun Preview() {
    UiPlaygroundPreview {
        DemoCircle()
    }
}