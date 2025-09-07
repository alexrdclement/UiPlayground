package com.alexrdclement.uiplayground.shaders

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp

private const val UniformShaderName = "composable"
private const val UniformSize = "size"
private const val UniformPoint = "point"
private const val UniformRadius = "radius"
private const val UniformAmount = "amount"

// SKSL
private var ShaderSource = """
uniform shader $UniformShaderName;
uniform float2 $UniformSize;
uniform float2 $UniformPoint;
uniform float $UniformRadius;
uniform float $UniformAmount;

half4 main(float2 fragCoord) {
    float2 pos = fragCoord - $UniformPoint;
    float dist = distance(fragCoord, $UniformPoint);
    if (dist < $UniformRadius) {
        float normalizedDist = dist / $UniformRadius;

        // The farther from the center, the stronger the effect
        float falloff = normalizedDist * normalizedDist;

        fragCoord = fragCoord - pos * $UniformAmount * falloff;
    }
    return $UniformShaderName.eval(fragCoord);
}
"""

actual fun createWarpShader(
    configure: WarpShader.() -> Unit
): WarpShader {
    return WarpShaderImpl(configure)
}

class WarpShaderImpl(
    configure: WarpShader.() -> Unit
): WarpShader {
    private val control = createShaderControl(ShaderSource, UniformShaderName, configure = { configure() })
    private var density: Density = Density(1f)

    override fun createRenderEffect(): RenderEffect? {
        return control.createRenderEffect()
    }

    override fun onRemeasured(width: Int, height: Int) {
        control.setFloatUniform(UniformSize, width.toFloat(), height.toFloat())
    }

    override fun onDensityChanged(density: Density) {
        this.density = density
    }

    override fun setOffset(offset: Offset) {
        control.setFloatUniform(UniformPoint, offset.x, offset.y)
    }

    override fun setRadius(radius: Dp) {
        val radiusPx = with(density) { radius.toPx() }
        control.setFloatUniform(UniformRadius, radiusPx)
    }

    override fun setAmount(amount: Float) {
        control.setFloatUniform(UniformAmount, amount)
    }
}
