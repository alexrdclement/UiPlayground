package com.alexrdclement.uiplayground.shaders

import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.ui.graphics.asComposeRenderEffect
import org.jetbrains.skia.ImageFilter
import org.jetbrains.skia.RuntimeEffect
import org.jetbrains.skia.RuntimeShaderBuilder

internal actual fun createShaderControl(
    source: String,
    uniformName: String,
    configure: ShaderControl.() -> Unit
): ShaderControl {
    return ShaderControlImpl(source, uniformName, configure)
}

private class ShaderControlImpl(
    source: String,
    val uniformName: String,
    val configure: ShaderControl.() -> Unit,
): ShaderControl {
    private val runtimeEffect = RuntimeEffect.makeForShader(source)
    private val runtimeShaderBuilder = RuntimeShaderBuilder(runtimeEffect)

    override fun createRenderEffect(): RenderEffect {
        configure()

        return ImageFilter.makeRuntimeShader(
            runtimeShaderBuilder = runtimeShaderBuilder,
            shaderName = uniformName,
            input = null,
        ).asComposeRenderEffect()
    }

    override fun setFloatUniform(name: String, value: Float) {
        runtimeShaderBuilder.uniform(name, value)
    }

    override fun setFloatUniform(name: String, value1: Float, value2: Float) {
        runtimeShaderBuilder.uniform(name, value1, value2)
    }

    override fun setIntUniform(name: String, value: Int) {
        runtimeShaderBuilder.uniform(name, value)
    }
}
