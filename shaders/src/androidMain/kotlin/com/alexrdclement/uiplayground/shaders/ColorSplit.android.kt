package com.alexrdclement.uiplayground.shaders

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.shaders.preview.DemoCircle
import com.alexrdclement.uiplayground.shaders.preview.ShaderPreview
import org.intellij.lang.annotations.Language

// Inspired by
// - Romain Guy on Coding with the Italians: https://www.youtube.com/watch?v=s5RibxKdo-o
// - Rikin Marfatia video: https://www.youtube.com/watch?v=hjJesq71UXc

private const val UniformShaderName = "composable"
private const val UniformSizeName = "size"
private const val UniformXAmountName = "xAmount"
private const val UniformYAmountName = "yAmount"
private const val UniformColorModeName = "colorMode"

@Language("AGSL")
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

actual fun createColorSplitShader(
    configure: ColorSplitShader.() -> Unit
): ColorSplitShader {
    return ColorSplitShaderImpl(configure)
}

class ColorSplitShaderImpl(
    configure: ColorSplitShader.() -> Unit,
) : ColorSplitShader {

    private val shader = createShaderControl(ShaderSource, UniformShaderName, configure = { configure() })

    override fun createRenderEffect(): RenderEffect? {
        return shader.createRenderEffect()
    }

    override fun setXAmount(xAmount: Float) {
        shader.setFloatUniform(UniformXAmountName, xAmount)
    }

    override fun setYAmount(yAmount: Float) {
        shader.setFloatUniform(UniformYAmountName, yAmount)
    }

    override fun setColorMode(mode: ColorSplitMode) {
        shader.setIntUniform(UniformColorModeName, mode.ordinal)
    }

    override fun onRemeasured(width: Int, height: Int) {
        shader.setFloatUniform(UniformSizeName, width.toFloat(), height.toFloat())
    }
}

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
