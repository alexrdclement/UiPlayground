package com.alexrdclement.uiplayground.shaders.util

import androidx.compose.ui.graphics.GraphicsContext
import androidx.compose.ui.graphics.layer.GraphicsLayer

// From Haze: https://github.com/chrisbanes/haze/blob/main/haze/src/commonMain/kotlin/dev/chrisbanes/haze/Canvas.kt
internal inline fun GraphicsContext.useGraphicsLayer(block: GraphicsLayer.() -> Unit) {
    val layer = createGraphicsLayer()
    try {
        block(layer)
    } finally {
        releaseGraphicsLayer(layer)
    }
}
