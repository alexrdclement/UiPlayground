package com.alexrdclement.uiplayground.log

import com.alexrdclement.uiplayground.loggable.Loggable

data class Log(
    val level: LogLevel,
    val tag: String?,
    val loggable: Loggable,
) {
    val message: String
        get() = loggable.message
}
