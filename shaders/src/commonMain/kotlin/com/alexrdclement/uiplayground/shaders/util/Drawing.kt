package com.alexrdclement.uiplayground.shaders.util

import androidx.compose.ui.graphics.GraphicsContext
import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.ui.graphics.drawscope.ContentDrawScope

expect fun ContentDrawScope.drawContentWithRenderEffect(
    renderEffect: RenderEffect,
    graphicsContext: GraphicsContext,
)
