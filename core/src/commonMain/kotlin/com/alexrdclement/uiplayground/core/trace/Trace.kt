package com.alexrdclement.uiplayground.core.trace

expect inline fun <T> trace(label: String, block: () -> T): T
