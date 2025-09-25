package com.alexrdclement.uiplayground.app.demo.control

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.ui.Modifier
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlin.enums.EnumEntries

sealed class Control {
    data class Slider(
        val name: () -> String,
        val value: () -> Float,
        val onValueChange: (Float) -> Unit,
        val valueRange: () -> ClosedFloatingPointRange<Float> = { 0f..1f }
    ) : Control() {
        constructor(
            name: String,
            value: () -> Float,
            onValueChange: (Float) -> Unit,
            valueRange: () -> ClosedFloatingPointRange<Float> = { 0f..1f }
        ) : this(
            name = { name },
            value = value,
            onValueChange = onValueChange,
            valueRange = valueRange,
        )
    }

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
        val modifier: Modifier = Modifier,
    ) : Control()

    data class ControlRow(val controls: () -> ImmutableList<Control>) : Control()

    data class ControlColumn(
        val controls: () -> ImmutableList<Control>,
        val name: String? = null,
        val indent: Boolean = false,
    ) : Control()
}

inline fun <T : Enum<T>> enumControl(
    name: String,
    crossinline values: () -> EnumEntries<T>,
    crossinline selectedValue: () -> Enum<T>,
    crossinline onValueChange: (T) -> Unit,
    includeLabel: Boolean = true
): Control.Dropdown<T> {
    return Control.Dropdown(
        name = name,
        values = {
            values().map {
                Control.Dropdown.DropdownItem(
                    name = it.name,
                    value = it,
                )
            }.toPersistentList()
        },
        selectedIndex = { values().indexOf(selectedValue()) },
        onValueChange = { index -> onValueChange(values()[index]) },
        includeLabel = includeLabel,
    )
}
