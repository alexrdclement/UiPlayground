package com.alexrdclement.uiplayground.loggable

interface Loggable {
    val message: String
    val throwable: Throwable? get() = null
}
