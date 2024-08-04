package com.alexrdclement.uiplayground.demo.shaders

import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.unit.Dp
import com.alexrdclement.uiplayground.shaders.ColorSplitMode

sealed class DemoModifier(open val name: String) {
    data object None : DemoModifier(name = "None")

    data class Blur(
        val radius: Dp,
        val edgeTreatment: BlurredEdgeTreatment,
    ) : DemoModifier(name = "Blur")

    data class ColorSplit(
        val xAmount: Float,
        val yAmount: Float,
        val colorMode: ColorSplitMode,
    ) : DemoModifier(name = "Color Split")

    data class Noise(
        val amount: Float,
        val colorEnabled: Boolean,
    ) : DemoModifier(name = "Noise")

    data class Pixelate(
        val subdivisions: Int,
    ) : DemoModifier(name = "Pixelate")
}
