package com.alexrdclement.uiplayground.components

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.components.util.mapSaverSafe
import kotlin.math.ln
import kotlin.math.pow

sealed class GridScale {
    data class Linear(
        val spacing: Dp,
    ) : GridScale()

    data class Logarithmic(
        val spacing: Dp,
        val base: Float = 10f,
    ) : GridScale()

    data class LogarithmicDecay(
        val spacing: Dp,
        val base: Float = 10f,
    ) : GridScale()

    data class ExponentialDecay(
        val spacing: Dp,
        val exponent: Float = 10f,
    ) : GridScale()

    data class Exponential(
        val spacing: Dp,
        val exponent: Float = 10f,
    ) : GridScale()

    fun scale(interval: Int, density: Density): Float = with(density) {
        return when (this@GridScale) {
            is Linear -> spacing.toPx()
            is Logarithmic -> if (interval == 0) 1f else {
                val scaling = ln(base.toDouble()).toFloat() * interval
                spacing.toPx() * scaling
            }
            is LogarithmicDecay -> if (interval == 0) 1f else {
                val scaling = 1 / (ln(base.toDouble()).toFloat() * interval)
                (spacing.toPx() * scaling).coerceAtLeast(1f)
            }
            is Exponential -> if (interval == 0) spacing.toPx() else {
                val scaling = exponent.toDouble().pow(interval.toDouble()).toFloat()
                spacing.toPx() * scaling
            }
            is ExponentialDecay -> if (interval == 0) spacing.toPx() else {
                val scaling = 1 / exponent.toDouble().pow(interval.toDouble()).toFloat()
                (spacing.toPx() * scaling).coerceAtLeast(1f)
            }
        }
    }
}

private enum class ScaleType {
    Linear,
    Logarithmic,
    LogarithmicDecay,
    Exponential,
    ExponentialDecay,
}

private const val scaleTypeKey = "scaleType"
private const val spacingKey = "spacing"
private const val baseKey = "base"
private const val exponentKey = "exponent"

val GridScaleSaver = mapSaverSafe(
    save = { value ->
        when (value) {
            is GridScale.Linear -> mapOf(
                scaleTypeKey to ScaleType.Linear,
                spacingKey to value.spacing.value,
            )
            is GridScale.Logarithmic -> mapOf(
                scaleTypeKey to ScaleType.Logarithmic,
                spacingKey to value.spacing.value,
                baseKey to value.base,
            )
            is GridScale.LogarithmicDecay -> mapOf(
                scaleTypeKey to ScaleType.LogarithmicDecay,
                spacingKey to value.spacing.value,
                baseKey to value.base,
            )
            is GridScale.Exponential -> mapOf(
                scaleTypeKey to ScaleType.Exponential,
                spacingKey to value.spacing.value,
                exponentKey to value.exponent,
            )
            is GridScale.ExponentialDecay -> mapOf(
                scaleTypeKey to ScaleType.ExponentialDecay,
                spacingKey to value.spacing.value,
                exponentKey to value.exponent,
            )
        }
    },
    restore = { map ->
        when (map[scaleTypeKey]) {
            ScaleType.Linear -> GridScale.Linear(
                spacing = (map[spacingKey] as Float).dp,
            )
            ScaleType.Logarithmic -> GridScale.Logarithmic(
                spacing = (map[spacingKey] as Float).dp,
                base = map[baseKey] as Float,
            )
            ScaleType.LogarithmicDecay -> GridScale.LogarithmicDecay(
                spacing = (map[spacingKey] as Float).dp,
                base = map[baseKey] as Float,
            )
            ScaleType.Exponential -> GridScale.Exponential(
                spacing = (map[spacingKey] as Float).dp,
                exponent = map[exponentKey] as Float,
            )
            ScaleType.ExponentialDecay -> GridScale.ExponentialDecay(
                spacing = (map[spacingKey] as Float).dp,
                exponent = map[exponentKey] as Float,
            )
            else -> throw IllegalArgumentException("Unknown type: ${map[scaleTypeKey]}")
        }
    }
)
