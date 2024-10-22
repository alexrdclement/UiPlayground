package com.alexrdclement.uiplayground.shaders

import androidx.compose.ui.graphics.RenderEffect

interface Shader {
    fun createRenderEffect(): RenderEffect?
    fun onRemeasured(width: Int, height: Int) {}
}
