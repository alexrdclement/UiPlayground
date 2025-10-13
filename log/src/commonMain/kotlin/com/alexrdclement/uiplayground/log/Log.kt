package com.alexrdclement.uiplayground.log

data class Log(
    val level: LogLevel,
    val tag: String?,
    val loggable: Loggable,
) {
    val message: String
        get() = loggable.message
}
