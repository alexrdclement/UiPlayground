package com.alexrdclement.uiplayground.components.money

internal actual fun getDecimalFormatSymbols(): DecimalFormatSymbols {
    val jvmSymbols = java.text.DecimalFormatSymbols.getInstance()
    return DecimalFormatSymbols(
        currencySymbol = jvmSymbols.currencySymbol,
        decimalSeparator = jvmSymbols.decimalSeparator,
        groupingSeparator = jvmSymbols.groupingSeparator,
    )
}
