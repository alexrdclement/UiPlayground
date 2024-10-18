package com.alexrdclement.uiplayground.shaders

import android.annotation.SuppressLint
import android.graphics.RuntimeShader
import android.os.Build
import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.ui.graphics.asComposeRenderEffect

internal actual fun createShaderControl(
    source: String,
    uniformName: String,
    configure: ShaderControl.() -> Unit
): ShaderControl {
    return ShaderControlImpl(source, uniformName, configure)
}

@SuppressLint("NewApi")
private class ShaderControlImpl(
    source: String,
    val uniformName: String,
    val configure: ShaderControl.() -> Unit,
): ShaderControl {
    private val runtimeShader: RuntimeShader? = createRuntimeShader(source)

    override fun createRenderEffect(): RenderEffect? {
        if (runtimeShader == null) {
            return null
        }

        configure()

        return android.graphics.RenderEffect
            .createRuntimeShaderEffect(runtimeShader, uniformName)
            .asComposeRenderEffect()
    }

    override fun setFloatUniform(name: String, value: Float) {
        runtimeShader?.setFloatUniform(name, value)
    }

    override fun setFloatUniform(name: String, value1: Float, value2: Float) {
        runtimeShader?.setFloatUniform(name, value1, value2)
    }

    override fun setIntUniform(name: String, value: Int) {
        runtimeShader?.setIntUniform(name, value)
    }

    private fun createRuntimeShader(shaderSource: String): RuntimeShader? {
        if (Build.VERSION.SDK_INT < 33) {
            return null
        }
        return RuntimeShader(shaderSource)
    }
}
