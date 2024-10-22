package com.alexrdclement.uiplayground.shaders

import androidx.compose.ui.graphics.RenderEffect

private const val UniformShaderName = "composable"
private const val UniformSize = "size"
private const val UniformAmount = "amount"
private const val UniformColorEnabled = "colorEnabledInt"
private const val UniformFilterBlack = "filterBlackInt"

// SKSL
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
}
