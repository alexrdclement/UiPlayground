package com.alexrdclement.uiplayground.components

internal actual fun getDecimalFormatSymbols(): DecimalFormatSymbols {
    return DecimalFormatSymbols(
        currencySymbol = "$",
        decimalSeparator = '.',
        groupingSeparator = ',',
    )
}
