package com.alexrdclement.uiplayground.shaders

import androidx.compose.ui.Modifier

enum class NoiseColorMode {
    Monochrome,
    RandomColor,
    RandomColorFilterBlack,
}

/**
 * @param amount: Returns the amount of noise to apply between 0f and 1f where 0f is none and 1f is
 * the maximum amount.
 */
fun Modifier.noise(
    colorMode: NoiseColorMode = NoiseColorMode.Monochrome,
    amount: () -> Float,
): Modifier = this then ShaderElement(
    shader = createNoiseShader {
        setColorMode(colorMode)
        setAmount(amount())
    },
    traceLabel = "noise",
)

interface NoiseShader : Shader {
    fun setColorMode(colorMode: NoiseColorMode)
    fun setAmount(amount: Float)
}

expect fun createNoiseShader(
    configure: NoiseShader.() -> Unit
): NoiseShader
