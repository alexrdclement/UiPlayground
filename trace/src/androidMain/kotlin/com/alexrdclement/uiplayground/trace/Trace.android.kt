package com.alexrdclement.uiplayground.trace

import androidx.tracing.trace

actual inline fun <T> trace(label: String, block: () -> T): T = trace(label, block)
