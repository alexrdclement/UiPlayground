package com.alexrdclement.uiplayground.app.demo.components.demo

import kotlin.math.exp
import kotlin.math.ln

fun linearToLogScale(value: Float, range: ClosedFloatingPointRange<Float>): Float {
    val minp = ln(range.start)
    val maxp = ln(range.endInclusive)
    val scale = (maxp - minp) / (range.endInclusive - range.start)
    return exp(minp + scale * (value - range.start))
}

fun logToLinearScale(value: Float, range: ClosedFloatingPointRange<Float>): Float {
    val minp = ln(range.start)
    val maxp = ln(range.endInclusive)
    val scale = (maxp - minp) / (range.endInclusive - range.start)
    return (ln(value) - minp) / scale + range.start
}
