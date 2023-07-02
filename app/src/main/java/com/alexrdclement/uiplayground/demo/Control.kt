package com.alexrdclement.uiplayground.demo

sealed class Control {
    data class Slider(
        val name: String,
        val value: Float,
        val onValueChange: (Float) -> Unit,
        val valueRange: ClosedFloatingPointRange<Float> = 0f..1f
    ) : Control()
}
