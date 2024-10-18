package com.alexrdclement.uiplayground.shaders

import androidx.compose.ui.graphics.RenderEffect

private const val UniformShaderName = "composable"
private const val UniformSizeName = "size"
private const val UniformSubdivisionsName = "subdivisions"

// TODO: convert to sksl
private var ShaderSource = """
uniform shader $UniformShaderName;
uniform float2 $UniformSizeName;
uniform float $UniformSubdivisionsName;

half4 main(float2 fragCoord) {
    float2 newCoord = fragCoord;
    newCoord.x -= mod(fragCoord.x, subdivisions + 1);
    newCoord.y -= mod(fragCoord.y, subdivisions + 1);
    return composable.eval(newCoord);
}
"""

actual fun createPixelateShader(
    configure: PixelateShader.() -> Unit
): PixelateShader {
    return PixelateShaderImpl(configure)
}

class PixelateShaderImpl(
    configure: PixelateShader.() -> Unit
): PixelateShader {
    private val shader = createShaderControl(ShaderSource, UniformShaderName, configure = { configure() })

    override fun createRenderEffect(): RenderEffect? {
        return shader.createRenderEffect()
    }

    override fun setSubdivisions(subdivisions: Int) {
        shader.setFloatUniform(UniformSubdivisionsName, subdivisions.toFloat())
    }

    override fun onRemeasured(width: Int, height: Int) {
        shader.setFloatUniform(UniformSizeName, width.toFloat(), height.toFloat())
    }
}
