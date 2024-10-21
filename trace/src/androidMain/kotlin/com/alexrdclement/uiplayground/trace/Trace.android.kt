package com.alexrdclement.uiplayground.trace

actual inline fun <T> trace(label: String, block: () -> T): T = androidx.tracing.trace(label, block)
