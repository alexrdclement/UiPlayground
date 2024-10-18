package com.alexrdclement.uiplayground.shaders

import androidx.compose.ui.Modifier

enum class ColorSplitMode {
    RGB,
    CMYK,
    RGBInverted,
}

/**
 * @param xAmount: Returns the amount of shift to apply on the X axis where 0f applies none
 * and 1f offsets by the full width of the target.
 * @param xAmount: Returns the amount of shift to apply on the Y axis where 0f applies none
 * and 1f offsets by the full height of the target.
 * @param colorMode: The output color space.
 */
fun Modifier.colorSplit(
    xAmount: () -> Float = { 0f },
    yAmount: () -> Float = { 0f },
    colorMode: () -> ColorSplitMode = { ColorSplitMode.RGB },
): Modifier = this then ShaderElement(
    shader = createColorSplitShader {
        setXAmount(xAmount())
        setYAmount(yAmount())
        setColorMode(colorMode())
    },
    traceLabel = "colorSplit",
)

interface ColorSplitShader : Shader {
    fun setXAmount(xAmount: Float)
    fun setYAmount(yAmount: Float)
    fun setColorMode(mode: ColorSplitMode)
}

expect fun createColorSplitShader(
    configure: ColorSplitShader.() -> Unit
): ColorSplitShader
