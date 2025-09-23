package com.alexrdclement.uiplayground.app.demo.control

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldState
import kotlinx.collections.immutable.ImmutableList

sealed class Control {
    data class Slider(
        val name: String,
        val value: () -> Float,
        val onValueChange: (Float) -> Unit,
        val valueRange: () -> ClosedFloatingPointRange<Float> = { 0f..1f }
    ) : Control()

    data class Dropdown<T>(
        val name: String,
        val values: () -> ImmutableList<DropdownItem<T>>,
        val selectedIndex: () -> Int,
        val onValueChange: (index: Int) -> Unit,
        val includeLabel: Boolean = true,
    ) : Control() {
        data class DropdownItem<T>(val name: String, val value: T)
    }

    data class Toggle(
        val name: String,
        val value: () -> Boolean,
        val onValueChange: (Boolean) -> Unit,
    ) : Control()

    data class TextField(
        val name: String,
        val textFieldState: TextFieldState,
        val includeLabel: Boolean = true,
        val enabled: () -> Boolean = { true },
        val keyboardOptions: () -> KeyboardOptions = { KeyboardOptions.Default },
        val inputTransformation: () -> InputTransformation? = { null },
    ) : Control()

    data class Button(
        val name: String,
        val onClick: () -> Unit,
        val enabled: () -> Boolean = { true },
    ) : Control()

    data class ControlRow(val controls: () -> ImmutableList<Control>) : Control()

    data class ControlColumn(
        val controls: () -> ImmutableList<Control>,
        val name: String? = null,
        val indent: Boolean = false,
    ) : Control()
}
