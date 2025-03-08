package com.alexrdclement.uiplayground.app.demo.control

import androidx.compose.foundation.text.input.TextFieldState

sealed class Control {
    data class Slider(
        val name: String,
        val value: Float,
        val onValueChange: (Float) -> Unit,
        val valueRange: ClosedFloatingPointRange<Float> = 0f..1f
    ) : Control()

    data class Dropdown<T>(
        val name: String,
        val values: List<DropdownItem<T>>,
        val selectedIndex: Int,
        val onValueChange: (index: Int) -> Unit
    ) : Control() {
        data class DropdownItem<T>(val name: String, val value: T)
    }

    data class Toggle(
        val name: String,
        val value: Boolean,
        val onValueChange: (Boolean) -> Unit,
    ) : Control()

    data class TextField(
        val name: String,
        val textFieldState: TextFieldState,
        val includeLabel: Boolean = true,
    ) : Control()
}
