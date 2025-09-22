package com.alexrdclement.uiplayground.components.money

internal actual fun getDecimalFormatSymbols(): DecimalFormatSymbols {
    return DecimalFormatSymbols(
        currencySymbol = "$",
        decimalSeparator = '.',
        groupingSeparator = ',',
    )
}
