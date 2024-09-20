package com.alexrdclement.uiplayground.demo.experiments.demo.basictextfield

import androidx.annotation.CheckResult
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.delete
import androidx.compose.foundation.text.input.insert
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import com.alexrdclement.uiplayground.ui.preview.UiPlaygroundPreview
import java.text.DecimalFormatSymbols
import kotlin.math.min

private val decimalFormatSymbols = DecimalFormatSymbols.getInstance()
private val currencySymbol = decimalFormatSymbols.currencySymbol
private val decimalSeparator = decimalFormatSymbols.decimalSeparator
private val decimalSeparatorStr = decimalSeparator.toString()
private val groupingSeparator = decimalFormatSymbols.groupingSeparator.toString()

@Composable
fun CurrencyAmountField(
    textFieldState: TextFieldState = rememberTextFieldState(),
    placeholder: String = "0",
    includeCurrencyPrefix: Boolean = true,
    maxNumDecimalValues: Int = 2
) {
    BasicTextField(
        state = textFieldState,
        textStyle = PlaygroundTheme.typography.headline.copy(
            color = PlaygroundTheme.colorScheme.onSurfaceVariant,
        ),
        modifier = Modifier
            .width(IntrinsicSize.Min)
            .padding(8.dp)
            .background(PlaygroundTheme.colorScheme.surfaceVariant)
            .padding(8.dp),
        inputTransformation = CurrencyAmountFieldInputTransformation(maxNumDecimalValues),
        outputTransformation = CurrencyAmountFieldOutputTransformation,
        lineLimits = TextFieldLineLimits.SingleLine,
        decorator = { textField ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                if (includeCurrencyPrefix) {
                    BasicText(
                        currencySymbol,
                        style = PlaygroundTheme.typography.headline.copy(
                            color = PlaygroundTheme.colorScheme.onSurfaceVariant,
                        )
                    )
                }

                Box {
                    if (textFieldState.text.isEmpty()) {
                        BasicText(
                            placeholder,
                            style = PlaygroundTheme.typography.headline.copy(
                                color = PlaygroundTheme.colorScheme.onSurfaceVariant.copy(
                                    alpha = 0.5f,
                                ),
                            )
                        )
                    }

                    // Min width to ensure cursor still appears
                    Box(modifier = Modifier.widthIn(min = 8.dp)) {
                        textField()
                    }
                }
            }
        }
    )
}

private class CurrencyAmountFieldInputTransformation(
    private val maxNumDecimalValues: Int,
) : InputTransformation {

    override val keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Decimal,
    )

    override fun TextFieldBuffer.transformInput() {
        filterChars()
        filterConsecutiveDecimals()

        val parts = asCharSequence().split(decimalSeparatorStr)
        val intPart = parts.firstOrNull()?.filter { it.isDigit() } ?: ""
        val decimalPart = parts.getOrNull(1)?.filter { it.isDigit() }

        if (parts.size > 2) {
            // Instead of rejecting changes with multiple decimals, recalculate according to the
            // first one.
            replace(intPart.length + 1, length, decimalPart ?: "")
        }

        val filteredIntPart = filterIntPart(intPart, hasDecimalPart = decimalPart != null)

        filterDecimalPart(decimalPart, startIndex = filteredIntPart.length + 1)
    }

    private fun TextFieldBuffer.filterChars() {
        val proposed = asCharSequence()
        if (proposed.any { !it.isDigit() && it != decimalSeparator }) {
            // Reject changes for any non-digit, non-decimal characters
            revertAllChanges()
        }
    }

    private fun TextFieldBuffer.filterConsecutiveDecimals() {
        val proposed = asCharSequence()
        var prevChar = proposed.firstOrNull()
        for (char in proposed.drop(1)) {
            if (prevChar == decimalSeparator && char == decimalSeparator) {
                // Reject changes for consecutive decimals
                revertAllChanges()
                return
            }
            prevChar = char
        }
    }

    @CheckResult
    private fun TextFieldBuffer.filterIntPart(
        intPart: String,
        hasDecimalPart: Boolean,
    ): String {
        var mutableIntPart = intPart

        if (mutableIntPart.startsWith('0')) {
            // Allow single leading zero. Replace leading zero if followed by another digit.
            val indexOfFirstNonZero = mutableIntPart.indexOfFirst { it != '0' }
            val newIntPart = if (indexOfFirstNonZero > 0) {
                mutableIntPart.substring(indexOfFirstNonZero)
            } else {
                "0"
            }
            replace(0, mutableIntPart.length, newIntPart)
            mutableIntPart = newIntPart
        }

        if (mutableIntPart.isEmpty() && hasDecimalPart) {
            // Prefill 0 when only decimal part is entered
            val newIntPart = "0"
            replace(0, mutableIntPart.length, newIntPart)
            mutableIntPart = newIntPart
        }

        return mutableIntPart
    }

    private fun TextFieldBuffer.filterDecimalPart(
        decimalPart: String?,
        startIndex: Int,
    ) {
        if (decimalPart != null && decimalPart.length > maxNumDecimalValues) {
            // Replace chars one-by-one so the cursor advances as expected
            for (index in startIndex until min(maxNumDecimalValues, decimalPart.length)) {
                replace(index, index + 1, decimalPart[index].toString())
            }
            delete(startIndex + maxNumDecimalValues, length)
        }
    }
}

private object CurrencyAmountFieldOutputTransformation: OutputTransformation {
    override fun TextFieldBuffer.transformOutput() {
        // Insert grouping separators every 3 digits
        val intPart = originalText.split(decimalSeparatorStr, limit = 2).firstOrNull() ?: ""
        for (index in 1 until intPart.length) {
            if (index % 3 == 0) {
                insert(intPart.length - index, groupingSeparator)
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    UiPlaygroundPreview {
        val textFieldState = rememberTextFieldState(initialText = "")
        CurrencyAmountField(textFieldState = textFieldState)
    }
}
