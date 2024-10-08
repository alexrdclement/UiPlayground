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

// Inspired by
// - Romain Guy on Coding with the Italians: https://www.youtube.com/watch?v=s5RibxKdo-o
// - Rikin Marfatia video: https://www.youtube.com/watch?v=hjJesq71UXc

enum class ColorSplitMode {
    RGB,
    CMYK,
    RGBInverted,
}

private const val UniformShaderName = "composable"
private const val UniformSizeName = "size"
private const val UniformXAmountName = "xAmount"
private const val UniformYAmountName = "yAmount"
private const val UniformColorModeName = "colorMode"

private var ShaderSource = """
uniform shader $UniformShaderName;
uniform float2 $UniformSizeName;
uniform float $UniformXAmountName;
uniform float $UniformYAmountName;
uniform int $UniformColorModeName;

half4 CMYKtoRGB(half4 cmyk) {
    float c = cmyk.x;
    float m = cmyk.y;
    float y = cmyk.z;
    float k = cmyk.w;

    float invK = 1.0 - k;
    float r = 1.0 - min(1.0, c * invK + k);
    float g = 1.0 - min(1.0, m * invK + k);
    float b = 1.0 - min(1.0, y * invK + k);
    return clamp(half4(r, g, b, 1.0), 0.0, 1.0);
}

half4 RGBtoCMYK(half4 rgb) {
    float r = rgb.r;
    float g = rgb.g;
    float b = rgb.b;
    float k = min(1.0 - r, min(1.0 - g, 1.0 - b));
    float3 cmy = float3(0.0);
    float invK = 1.0 - k;
    if (invK != 0.0) {
        cmy.x = (1.0 - r - k) / invK;
        cmy.y = (1.0 - g - k) / invK;
        cmy.z = (1.0 - b - k) / invK;
    }
    return clamp(half4(cmy, k), 0.0, 1.0);
}

half4 main(float2 fragCoord) {
    float xDisplacement = 1.0 - size.x * xAmount;
    float yDisplacement = 1.0 - size.y * yAmount;
    half4 color = composable.eval(fragCoord);
    half4 displacedColor = half4(
        composable.eval(float2(fragCoord.x - xDisplacement, fragCoord.y - yDisplacement)).r,
        color.g,
        composable.eval(float2(fragCoord.x + xDisplacement, fragCoord.y + yDisplacement)).b,
        color.a
    );
    if (colorMode == 1) {
        return RGBtoCMYK(displacedColor);
    } else if (colorMode == 2) {
        return half4(1.0) - displacedColor;
    }
    return displacedColor;
}
"""

/**
 * @param xAmount: Returns the amount of shift to apply on the X axis where 0f applies none
 * and 1f offsets by the full width of the target.
 * @param xAmount: Returns the amount of shift to apply on the Y axis where 0f applies none
 * and 1f offsets by the full height of the target.
 * @param colorMode: The output color space.
 */
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun Modifier.colorSplit(
    xAmount: () -> Float = { 0f },
    yAmount: () -> Float = { 0f },
    colorMode: () -> ColorSplitMode = { ColorSplitMode.RGB },
): Modifier = this then ColorSplitElement(xAmount, yAmount, colorMode)

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
private data class ColorSplitElement(
    val xAmount: () -> Float,
    val yAmount: () -> Float,
    val colorMode: () -> ColorSplitMode,
) : ModifierNodeElement<ColorSplitNode>() {
    override fun create() = ColorSplitNode(
        xAmount = xAmount,
        yAmount = yAmount,
        colorMode = colorMode,
    )
    override fun update(node: ColorSplitNode) {
        node.xAmount = xAmount
        node.yAmount = yAmount
        node.colorMode = colorMode
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
private class ColorSplitNode(
    var xAmount: () -> Float,
    var yAmount: () -> Float,
    var colorMode: () -> ColorSplitMode,
) : Modifier.Node(),
    DrawModifierNode,
    LayoutAwareModifierNode,
    CompositionLocalConsumerModifierNode {

    private val shader = RuntimeShader(ShaderSource)

    override fun onRemeasured(size: IntSize) {
        shader.setFloatUniform(UniformSizeName, size.width.toFloat(), size.height.toFloat())
    }

    override fun ContentDrawScope.draw() {
        trace("colorSplit") {
            shader.setFloatUniform(UniformXAmountName, xAmount())
            shader.setFloatUniform(UniformYAmountName, yAmount())
            shader.setIntUniform(UniformColorModeName, colorMode().ordinal)

            val graphicsContext = currentValueOf(LocalGraphicsContext)
            drawContentWithShader(shader, UniformShaderName, graphicsContext)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@ShaderPreview
@Composable
private fun Preview() {
    val range = -1f..1f
    var xAmount by remember { mutableStateOf(range.endInclusive / 2f) }
    var yAmount by remember { mutableStateOf(range.endInclusive / 2f) }
    Column {
        DemoCircle(
            modifier = Modifier
                .weight(1f)
                .colorSplit(
                    xAmount = { xAmount },
                    yAmount = { yAmount }
                )
        )
        Slider(
            valueRange = range,
            value = xAmount,
            onValueChange = { xAmount = it },
            modifier = Modifier.padding(16.dp)
        )
        Slider(
            valueRange = range,
            value = yAmount,
            onValueChange = { yAmount = it },
            modifier = Modifier.padding(16.dp)
        )
    }
}
