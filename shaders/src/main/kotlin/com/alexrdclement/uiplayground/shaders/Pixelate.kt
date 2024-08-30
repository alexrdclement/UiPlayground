package com.alexrdclement.uiplayground.shaders

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
import kotlin.math.roundToInt

// Inspired by Rikin Marfatia's Pixellate
// https://github.com/Rahkeen/ShaderPlayground/blob/main/app/src/main/java/co/rikin/shaderplayground/shaders/PixellateShader.kt

private const val UniformShaderName = "composable"
private const val UniformSizeName = "size"
private const val UniformSubdivisionsName = "subdivisions"

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

/**
 * @param subdivisions: Returns the number of additional adjacent pixels to include in each pixel
 * block.
 */
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun Modifier.pixelate(
    subdivisions: () -> Int,
): Modifier = this then PixelateElement(subdivisions)

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
private data class PixelateElement(val subdivisions: () -> Int) : ModifierNodeElement<PixelateNode>() {
    override fun create() = PixelateNode(subdivisions = subdivisions)
    override fun update(node: PixelateNode) {
        node.subdivisions = subdivisions
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
private class PixelateNode(
    var subdivisions: () -> Int,
) : Modifier.Node(),
    DrawModifierNode,
    LayoutAwareModifierNode,
    CompositionLocalConsumerModifierNode {

    private val shader = RuntimeShader(ShaderSource)

    override fun onRemeasured(size: IntSize) {
        shader.setFloatUniform(UniformSizeName, size.width.toFloat(), size.height.toFloat())
    }

    override fun ContentDrawScope.draw() {
        trace("pixelate") {
            shader.setFloatUniform(UniformSubdivisionsName, subdivisions().toFloat())

            val graphicsContext = currentValueOf(LocalGraphicsContext)
            drawContentWithShader(shader, UniformShaderName, graphicsContext)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@ShaderPreview
@Composable
private fun Preview() {
    val range = 0f..100f
    var amount by remember { mutableStateOf(range.endInclusive / 2f) }
    Column {
        DemoCircle(
            modifier = Modifier
                .weight(1f)
                .pixelate(subdivisions = { amount.roundToInt() })
        )
        Slider(
            valueRange = range,
            value = amount,
            onValueChange = { amount = it },
            modifier = Modifier.padding(16.dp)
        )
    }
}
