package com.alexrdclement.uiplayground.trace

actual fun <T> trace(label: String, block: () -> T): T {
    return block()
}
