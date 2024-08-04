package com.alexrdclement.uiplayground.shaders

import android.graphics.RuntimeShader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.LayoutAwareModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.currentValueOf
import androidx.compose.ui.platform.LocalGraphicsContext
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.tracing.trace
import com.alexrdclement.uiplayground.shaders.preview.DemoCircle
import com.alexrdclement.uiplayground.shaders.preview.ShaderPreview
import android.graphics.RenderEffect as AndroidRenderEffect

// Inspired by Rikin Marfatia's Grainy Gradients https://www.youtube.com/watch?v=soMl3k0mBx4

private const val UniformShaderName = "composable"
private const val UniformSize = "size"
private const val UniformAmount = "amount"
private const val UniformColorEnabled = "colorEnabled"

private var ShaderSource = """
uniform shader $UniformShaderName;
uniform float2 $UniformSize;
uniform float $UniformAmount;
uniform int $UniformColorEnabled;

// From https://thebookofshaders.com/10/
float noise(float2 fragCoord) {
    return fract(sin(dot(fragCoord.xy, float2(12.9898, 78.233))) * 43758.5453123);
}

half4 main(float2 fragCoord) {
    half4 color = composable.eval(fragCoord);

    float noiseVal = noise(fragCoord);

    if (colorEnabled > 0 && noiseVal > 1 - amount) {
        color.rgb = vec3(noise(fragCoord + 0.1), noise(fragCoord + 0.2), noise(fragCoord + 0.3));
    }

    color.rgb *= 1 - noiseVal * amount; 
    
    return color;
}
"""

/**
 * @param amount: Returns the amount of noise to apply between 0f and 1f where 0f is none and 1f is
 * the maximum amount.
 */
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun Modifier.noise(
    colorEnabled: Boolean = false,
    amount: () -> Float,
): Modifier = this then NoiseElement(amount = amount, colorEnabled = colorEnabled)

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
private data class NoiseElement(
    val amount: () -> Float,
    val colorEnabled: Boolean,
) : ModifierNodeElement<NoiseNode>() {
    override fun create() = NoiseNode(amount = amount, colorEnabled = colorEnabled)
    override fun update(node: NoiseNode) {
        node.amount = amount
        node.colorEnabled = colorEnabled
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
private class NoiseNode(
    var amount: () -> Float,
    var colorEnabled: Boolean,
) : Modifier.Node(),
    DrawModifierNode,
    LayoutAwareModifierNode,
    CompositionLocalConsumerModifierNode {

    private val shader = RuntimeShader(ShaderSource)

    override fun onRemeasured(size: IntSize) {
        shader.setFloatUniform(UniformSize, size.width.toFloat(), size.height.toFloat())
    }

    override fun ContentDrawScope.draw() {
        trace("noise") {
            shader.setFloatUniform(UniformAmount, amount())
            shader.setIntUniform(UniformColorEnabled, if (colorEnabled) 1 else 0)

            val graphicsContext = currentValueOf(LocalGraphicsContext)
            graphicsContext.useGraphicsLayer {
                clip = true
                renderEffect = AndroidRenderEffect
                    .createRuntimeShaderEffect(shader, UniformShaderName)
                    .asComposeRenderEffect()

                record { this@draw.drawContent() }

                drawLayer(this)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@ShaderPreview
@Composable
private fun Preview() {
    val range = 0f..1f
    var amount by remember { mutableFloatStateOf(range.endInclusive / 2f) }
    var color by remember { mutableStateOf(true) }
    Column {
        DemoCircle(
            modifier = Modifier
                .weight(1f)
                .noise(amount = { amount }, colorEnabled = color)
        )
        Slider(
            valueRange = range,
            value = amount,
            onValueChange = { amount = it },
            modifier = Modifier.padding(16.dp)
        )
        Switch(
            checked = color,
            onCheckedChange = { color = it},
            modifier = Modifier.padding(16.dp)
        )
    }
}
