package com.alexrdclement.uiplayground.shaders

import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.ui.unit.Density

interface Shader {
    fun createRenderEffect(): RenderEffect?
    fun onRemeasured(width: Int, height: Int) {}
    fun onDensityChanged(density: Density) {}
}
