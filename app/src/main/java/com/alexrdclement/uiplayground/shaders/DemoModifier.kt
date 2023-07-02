package com.alexrdclement.uiplayground.shaders

import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.unit.Dp

sealed class DemoModifier(open val name: String) {
    object None : DemoModifier(name = "None")

    data class Blur(
        val radius: Dp,
        val edgeTreatment: BlurredEdgeTreatment,
    ) : DemoModifier(name = "Blur")

    data class ChromaticAberration(
        val amount: Float,
    ) : DemoModifier(name = "Chromatic Aberration")
}
