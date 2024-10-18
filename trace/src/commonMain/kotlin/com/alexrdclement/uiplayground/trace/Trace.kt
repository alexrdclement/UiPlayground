package com.alexrdclement.uiplayground.trace

expect fun <T> trace(label: String, block: () -> T): T
