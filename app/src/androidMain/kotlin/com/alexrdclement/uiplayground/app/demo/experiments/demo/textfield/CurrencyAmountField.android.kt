package com.alexrdclement.uiplayground.app.demo.experiments.demo.textfield

internal actual fun getDecimalFormatSymbols(): DecimalFormatSymbols {
    val symbols = java.text.DecimalFormatSymbols.getInstance()
    return DecimalFormatSymbols(
        currencySymbol = symbols.currencySymbol,
        decimalSeparator = symbols.decimalSeparator,
        groupingSeparator = symbols.groupingSeparator,
    )
}
