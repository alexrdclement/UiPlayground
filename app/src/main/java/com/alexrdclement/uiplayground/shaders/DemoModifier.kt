package com.alexrdclement.uiplayground.shaders

import androidx.compose.ui.unit.Dp

sealed class DemoModifier(open val name: String) {
    object None : DemoModifier(name = "None")

    data class Blur(
        val radius: Dp
    ) : DemoModifier(name = "Blur")

    data class ChromaticAberration(
        val amount: Float
    ) : DemoModifier(name = "Chromatic Aberration")
}
