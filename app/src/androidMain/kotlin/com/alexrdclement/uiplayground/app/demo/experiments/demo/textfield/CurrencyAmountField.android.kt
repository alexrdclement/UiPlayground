package com.alexrdclement.uiplayground.app.demo.experiments.demo.textfield

import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.alexrdclement.uiplayground.app.preview.UiPlaygroundPreview

internal actual fun getDecimalFormatSymbols(): DecimalFormatSymbols {
    val symbols = java.text.DecimalFormatSymbols.getInstance()
    return DecimalFormatSymbols(
        currencySymbol = symbols.currencySymbol,
        decimalSeparator = symbols.decimalSeparator,
        groupingSeparator = symbols.groupingSeparator,
    )
}

@Preview
@Composable
private fun Preview() {
    UiPlaygroundPreview {
        val textFieldState = rememberTextFieldState(initialText = "")
        CurrencyAmountField(textFieldState = textFieldState)
    }
}
