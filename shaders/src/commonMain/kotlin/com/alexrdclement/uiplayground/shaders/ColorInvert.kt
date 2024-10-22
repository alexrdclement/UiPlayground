package com.alexrdclement.uiplayground.shaders

import androidx.compose.ui.Modifier

/**
 * @param amount: Returns the amount of color inversion to apply where 0f applies none and 1f fully
 * inverts the color.
 */
fun Modifier.colorInvert(
    amount: () -> Float,
): Modifier = this then ShaderElement(
    shader = createColorInvertShader {
        setAmount(amount())
    },
    traceLabel = "colorInvert",
)

interface ColorInvertShader : Shader {
    fun setAmount(amount: Float)
}

expect fun createColorInvertShader(
    configure: ColorInvertShader.() -> Unit
): ColorInvertShader
