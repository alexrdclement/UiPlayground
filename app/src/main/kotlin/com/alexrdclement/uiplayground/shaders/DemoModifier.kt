package com.alexrdclement.uiplayground.shaders

import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.unit.Dp

sealed class DemoModifier(open val name: String) {
    data object None : DemoModifier(name = "None")

    data class Blur(
        val radius: Dp,
        val edgeTreatment: BlurredEdgeTreatment,
    ) : DemoModifier(name = "Blur")

    data class ChromaticAberration(
        val xAmount: Float,
        val yAmount: Float,
        val colorMode: ChromaticAberrationColorMode,
    ) : DemoModifier(name = "Chromatic Aberration")

    data class Noise(
        val amount: Float,
    ) : DemoModifier(name = "Noise")

    data class Pixelate(
        val subdivisions: Int,
    ) : DemoModifier(name = "Pixelate")
}
