package com.alexrdclement.uiplayground.app.demo.util

import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.byValue

fun InputTransformation.onlyDigits() = byValue { current, proposed ->
    proposed.filter { it.isDigit() }
}

fun InputTransformation.onlyDigitsAndDecimalPoint() = byValue { current, proposed ->
    if (proposed.count { it == '.' } > 1) return@byValue current
    proposed.filter { it.isDigit() || it == '.' }
}
