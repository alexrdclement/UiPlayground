package com.alexrdclement.uiplayground.app.demo.experiments.demo.textfield

import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.byValue

fun InputTransformation.onlyDigits() = byValue { current, proposed ->
    proposed.filter{ it.isDigit() }
}
