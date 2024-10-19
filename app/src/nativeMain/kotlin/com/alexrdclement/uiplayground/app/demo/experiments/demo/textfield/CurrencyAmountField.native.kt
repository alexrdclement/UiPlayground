package com.alexrdclement.uiplayground.app.demo.experiments.demo.textfield

internal actual fun getDecimalFormatSymbols(): DecimalFormatSymbols {
    return DecimalFormatSymbols(
        currencySymbol = "$",
        decimalSeparator = '.',
        groupingSeparator = ',',
    )
}
