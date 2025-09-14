package com.alexrdclement.uiplayground.components

import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import kotlin.math.ln
import kotlin.math.pow

enum class GridScaleType {
    Linear,
    Logarithmic,
    LogarithmicDecay,
    Exponential,
    ExponentialDecay,
}

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
