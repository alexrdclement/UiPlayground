package com.alexrdclement.uiplayground.shaders

import androidx.compose.ui.Modifier

/**
 * @param subdivisions: Returns the number of additional adjacent pixels to include in each pixel
 * block.
 */
fun Modifier.pixelate(
    subdivisions: () -> Int,
): Modifier = this then ShaderElement(
    shader = createPixelateShader {
        setSubdivisions(subdivisions())
    },
    traceLabel = "pixelate",
)

interface PixelateShader : Shader {
    fun setSubdivisions(subdivisions: Int)
}

expect fun createPixelateShader(
    configure: PixelateShader.() -> Unit
): PixelateShader
