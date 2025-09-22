package com.alexrdclement.uiplayground.core.trace

actual inline fun <T> trace(label: String, block: () -> T): T {
    return block()
}
