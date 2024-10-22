package com.alexrdclement.uiplayground.trace

expect inline fun <T> trace(label: String, block: () -> T): T
