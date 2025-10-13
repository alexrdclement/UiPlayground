package com.alexrdclement.uiplayground.log

data class Log(
    val tag: String?,
    val loggable: Loggable,
) {
    val message: String
        get() = loggable.message
}
