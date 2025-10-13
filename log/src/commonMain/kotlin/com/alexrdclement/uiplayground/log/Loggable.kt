package com.alexrdclement.uiplayground.log

interface Loggable {
    val message: String
    val throwable: Throwable? get() = null
}
