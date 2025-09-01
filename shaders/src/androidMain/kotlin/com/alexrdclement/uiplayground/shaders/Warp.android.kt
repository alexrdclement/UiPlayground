package com.alexrdclement.uiplayground.shaders

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.shaders.preview.DemoCircle
import com.alexrdclement.uiplayground.shaders.preview.ShaderPreview
import org.intellij.lang.annotations.Language

private const val UniformShaderName = "composable"
private const val UniformSize = "size"
private const val UniformPoint = "point"
private const val UniformRadius = "radius"
private const val UniformAmount = "amount"

@Language("AGSL")
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

        // The farther the pixel is from the center, the stronger the effect
        float falloff = normalizedDist * normalizedDist;

        fragCoord = fragCoord - pos * $UniformAmount * falloff;
    }
    return $UniformShaderName.eval(fragCoord);;
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

@ShaderPreview
@Composable
private fun Preview() {
    val amountRange = 0f..1f
    val radiusRange = 10f..300f
    var point by remember { mutableStateOf(Offset(200f, 200f)) }
    var radius by remember { mutableStateOf(100.dp) }
    var amount by remember { mutableFloatStateOf(amountRange.endInclusive / 2f) }

    Column {
        DemoCircle(
            modifier = Modifier
                .weight(1f)
                .warp(
                    offset = { point },
                    radius = { radius },
                    amount = { amount },
                )
                .pointerInput(Unit) {
                    detectDragGestures { change, _ ->
                        change.consume()
                        point = change.position
                    }
                }
        )
        Slider(
            valueRange = radiusRange,
            value = radius.value,
            onValueChange = { radius = it.dp },
            modifier = Modifier.padding(16.dp)
        )
        Slider(
            valueRange = amountRange,
            value = amount,
            onValueChange = { amount = it },
            modifier = Modifier.padding(16.dp)
        )
    }
}
