package com.alexrdclement.uiplayground.app.demo.shaders

import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.unit.Dp
import com.alexrdclement.uiplayground.shaders.ColorSplitMode
import com.alexrdclement.uiplayground.shaders.NoiseColorMode

sealed class DemoModifier(open val name: String) {
    data object None : DemoModifier(name = "None")

    data class Blur(
        val radius: Dp,
        val edgeTreatment: BlurredEdgeTreatment,
    ) : DemoModifier(name = "Blur")

    data class ColorInvert(
        val amount: Float,
    ) : DemoModifier(name = "Color Invert")

    data class ColorSplit(
        val xAmount: Float,
        val yAmount: Float,
        val colorMode: ColorSplitMode,
    ) : DemoModifier(name = "Color Split")

    data class Noise(
        val colorMode: NoiseColorMode,
        val amount: Float,
    ) : DemoModifier(name = "Noise")

    data class Pixelate(
        val subdivisions: Int,
    ) : DemoModifier(name = "Pixelate")

    data class Warp(
        val radius: Dp,
        val amount: Float,
    ) : DemoModifier(name = "Warp")
}
