package com.alexrdclement.uiplayground.shaders

import android.graphics.RuntimeShader
import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
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
import com.alexrdclement.uiplayground.shaders.util.drawContentWithShader

// Inspired by Rikin Marfatia's Grainy Gradients https://www.youtube.com/watch?v=soMl3k0mBx4

private const val UniformShaderName = "composable"
private const val UniformSize = "size"
private const val UniformAmount = "amount"
private const val UniformColorEnabled = "colorEnabledInt"
private const val UniformFilterBlack = "filterBlackInt"

private var ShaderSource = """
uniform shader $UniformShaderName;
uniform float2 $UniformSize;
uniform float $UniformAmount;
uniform int $UniformColorEnabled;
uniform int $UniformFilterBlack;

// From https://thebookofshaders.com/10/
float noise(float2 fragCoord) {
    return fract(sin(dot(fragCoord.xy, float2(12.9898, 78.233))) * 43758.5453123);
}

half4 main(float2 fragCoord) {
    bool colorEnabled = colorEnabledInt > 0;
    bool filterBlack = filterBlackInt > 0;

    half4 color = composable.eval(fragCoord);
    bool isBlack = color == half4(0.0, 0.0, 0.0, 1.0);

    float noiseVal = noise(fragCoord);

    color.rgb *= 1 - noiseVal * amount; 

    if (colorEnabled && (!filterBlack || !isBlack) && noiseVal > 1 - amount) {
        color.rgb = vec3(noise(fragCoord + 0.1), noise(fragCoord + 0.2), noise(fragCoord + 0.3));
    }
    
    return color;
}
"""

enum class NoiseColorMode {
    Monochrome,
    RandomColor,
    RandomColorFilterBlack,
}

/**
 * @param amount: Returns the amount of noise to apply between 0f and 1f where 0f is none and 1f is
 * the maximum amount.
 */
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun Modifier.noise(
    colorMode: NoiseColorMode = NoiseColorMode.Monochrome,
    amount: () -> Float,
): Modifier = this then NoiseElement(colorMode, amount)

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
private data class NoiseElement(
    val colorMode: NoiseColorMode,
    val amount: () -> Float,
) : ModifierNodeElement<NoiseNode>() {
    override fun create() = NoiseNode(colorMode, amount)
    override fun update(node: NoiseNode) {
        node.colorMode = colorMode
        node.amount = amount
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
private class NoiseNode(
    var colorMode: NoiseColorMode,
    var amount: () -> Float,
) : Modifier.Node(),
    DrawModifierNode,
    LayoutAwareModifierNode,
    CompositionLocalConsumerModifierNode {

    private val shader = RuntimeShader(ShaderSource)

    private val colorEnabled: Int
        get() = when (colorMode) {
            NoiseColorMode.Monochrome -> 0
            else -> 1
        }

    private val filterBlack: Int
        get() = when (colorMode) {
            NoiseColorMode.RandomColorFilterBlack -> 1
            else -> 0
        }

    override fun onRemeasured(size: IntSize) {
        shader.setFloatUniform(UniformSize, size.width.toFloat(), size.height.toFloat())
    }

    override fun ContentDrawScope.draw() {
        trace("noise") {
            shader.setFloatUniform(UniformAmount, amount())
            shader.setIntUniform(UniformColorEnabled, colorEnabled)
            shader.setIntUniform(UniformFilterBlack, filterBlack)

            val graphicsContext = currentValueOf(LocalGraphicsContext)
            drawContentWithShader(shader, UniformShaderName, graphicsContext)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@ShaderPreview
@Composable
private fun Preview() {
    val range = 0f..1f
    var amount by remember { mutableFloatStateOf(range.endInclusive / 2f) }
    var colorMode by remember { mutableStateOf(NoiseColorMode.RandomColorFilterBlack) }

    Column {
        DemoCircle(
            modifier = Modifier
                .weight(1f)
                .noise(colorMode = colorMode, amount = { amount })
        )
        Slider(
            valueRange = range,
            value = amount,
            onValueChange = { amount = it },
            modifier = Modifier.padding(16.dp)
        )
    }
}
