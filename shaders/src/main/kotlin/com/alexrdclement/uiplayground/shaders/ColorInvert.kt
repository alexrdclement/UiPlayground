package com.alexrdclement.uiplayground.shaders

import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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

private const val UniformShaderName = "composable"
private const val UniformAmount = "amount"

private var ShaderSource = """
uniform shader $UniformShaderName;
uniform float $UniformAmount;

half4 main(float2 fragCoord) {
    half4 color = composable.eval(fragCoord);
    return half4(abs(amount - color.r), abs(amount - color.g), abs(amount - color.b), color.a);
}
"""

/**
 * @param amount: Returns the amount of color inversion to apply where 0f applies none and 1f fully
 * inverts the color.
 */
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun Modifier.colorInvert(
    amount: () -> Float,
): Modifier = this then ColorInvertElement(amount = amount)

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
private data class ColorInvertElement(
    val amount: () -> Float,
) : ModifierNodeElement<ColorInvertNode>() {
    override fun create() = ColorInvertNode(amount = amount)
    override fun update(node: ColorInvertNode) {
        node.amount = amount
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
private class ColorInvertNode(
    var amount: () -> Float,
) : Modifier.Node(),
    DrawModifierNode,
    CompositionLocalConsumerModifierNode {

    private val shader = RuntimeShader(ShaderSource)

    override fun ContentDrawScope.draw() {
        trace("colorInvert") {
            shader.setFloatUniform(UniformAmount, amount())

            val graphicsContext = currentValueOf(LocalGraphicsContext)
            graphicsContext.useGraphicsLayer {
                clip = true
                renderEffect = RenderEffect
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
    var amount by remember { mutableStateOf(range.endInclusive / 2f) }
    Column {
        DemoCircle(
            modifier = Modifier
                .weight(1f)
                .colorInvert(
                    amount = { amount }
                )
        )
        Slider(
            valueRange = range,
            value = amount,
            onValueChange = { amount = it },
            modifier = Modifier.padding(16.dp)
        )
    }
}
