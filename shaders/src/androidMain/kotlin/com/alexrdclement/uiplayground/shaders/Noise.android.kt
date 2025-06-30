package com.alexrdclement.uiplayground.shaders

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
import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.shaders.preview.DemoCircle
import com.alexrdclement.uiplayground.shaders.preview.ShaderPreview
import org.intellij.lang.annotations.Language

// Inspired by Rikin Marfatia's Grainy Gradients https://www.youtube.com/watch?v=soMl3k0mBx4

private const val UniformShaderName = "composable"
private const val UniformSize = "size"
private const val UniformAmount = "amount"
private const val UniformColorEnabled = "colorEnabledInt"
private const val UniformFilterBlack = "filterBlackInt"

@Language("AGSL")
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

    float2 scaledCoord = fragCoord.xy / $UniformSize;
    scaledCoord *= 1.0;
    
    float noiseVal = noise(scaledCoord);

    color.rgb *= 1 - noiseVal * amount; 

    if (colorEnabled && (!filterBlack || !isBlack) && noiseVal > 1 - amount) {
        color.rgb = vec3(noise(scaledCoord + 0.1), noise(scaledCoord + 0.2), noise(scaledCoord + 0.3));
    }
    
    return color;
}
"""

actual fun createNoiseShader(
    configure: NoiseShader.() -> Unit
): NoiseShader {
    return NoiseShaderImpl(configure)
}

class NoiseShaderImpl(
    configure: NoiseShader.() -> Unit
): NoiseShader {
    private val control = createShaderControl(ShaderSource, UniformShaderName, configure = { configure() })

    override fun createRenderEffect(): RenderEffect? {
        return control.createRenderEffect()
    }

    override fun setColorMode(colorMode: NoiseColorMode) {
        val colorEnabled = when (colorMode) {
            NoiseColorMode.Monochrome -> 0
            else -> 1
        }
        val filterBlack = when (colorMode) {
            NoiseColorMode.RandomColorFilterBlack -> 1
            else -> 0
        }
        control.setIntUniform(UniformColorEnabled, colorEnabled)
        control.setIntUniform(UniformFilterBlack, filterBlack)
    }

    override fun setAmount(amount: Float) {
        control.setFloatUniform(UniformAmount, amount)
    }

    override fun onRemeasured(width: Int, height: Int) {
        control.setFloatUniform(UniformSize, width.toFloat(), height.toFloat())
    }
}

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
