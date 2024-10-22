package com.alexrdclement.uiplayground.shaders

import androidx.compose.ui.graphics.RenderEffect

internal interface ShaderControl {
    fun createRenderEffect(): RenderEffect?
    fun setFloatUniform(name: String, value: Float)
    fun setFloatUniform(name: String, value1: Float, value2: Float)
    fun setIntUniform(name: String, value: Int)
}

internal expect fun createShaderControl(
    source: String,
    uniformName: String,
    configure: ShaderControl.() -> Unit
): ShaderControl
