package com.alexrdclement.uiplayground.shaders.util

import androidx.compose.ui.graphics.GraphicsContext
import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.layer.drawLayer

actual fun ContentDrawScope.drawContentWithRenderEffect(
    renderEffect: RenderEffect,
    graphicsContext: GraphicsContext,
) {
    graphicsContext.useGraphicsLayer {
        clip = true
        this.renderEffect = renderEffect

        record { this@drawContentWithRenderEffect.drawContent() }
        drawLayer(this)
    }
}
