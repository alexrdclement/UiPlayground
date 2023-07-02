package com.alexrdclement.uiplayground.shaders

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.demo.Circle
import com.alexrdclement.uiplayground.util.UiPlaygroundPreview

fun Modifier.blur(
    radius: Dp = 4.dp,
    edgeTreatment: BlurredEdgeTreatment = BlurredEdgeTreatment.Rectangle,
): Modifier {
    return this.blur(
        radius = radius,
        edgeTreatment = edgeTreatment,
    )
}

@Preview
@Composable
private fun Preview() {
    UiPlaygroundPreview {
        Circle(
            modifier = Modifier.blur()
        )
    }
}
