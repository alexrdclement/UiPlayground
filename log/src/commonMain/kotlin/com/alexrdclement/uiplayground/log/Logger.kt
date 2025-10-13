package com.alexrdclement.uiplayground.log

import kotlinx.coroutines.flow.Flow
import kotlin.jvm.JvmName

interface Logger {
    suspend fun log(
        level: LogLevel,
        tag: String? = null,
        loggable: () -> Loggable,
    )
    fun getLogFlow(
        level: LogLevel,
        exclusive: Boolean = false,
    ): Flow<Loggable>
}

suspend fun Logger.debug(
    tag: String? = null,
    loggable: () -> Loggable,
) = log(
    level = LogLevel.Debug,
    tag = tag,
    loggable = loggable,
)

suspend fun Logger.info(
    tag: String? = null,
    loggable: () -> Loggable,
) = log(
    level = LogLevel.Info,
    tag = tag,
    loggable = loggable
)

suspend fun Logger.warn(
    tag: String? = null,
    loggable: () -> Loggable,
) = log(
    level = LogLevel.Warn,
    tag = tag,
    loggable = loggable
)

suspend fun Logger.error(
    tag: String? = null,
    loggable: () -> Loggable,
) = log(
    level = LogLevel.Error,
    tag = tag,
    loggable = loggable
)

@JvmName("debugString")
suspend fun Logger.debug(
    tag: String? = null,
    loggable: () -> String,
) = log(
    level = LogLevel.Debug,
    tag = tag,
    loggable = loggable,
)

@JvmName("infoString")
suspend fun Logger.info(
    tag: String? = null,
    loggable: () -> String,
) = log(
    level = LogLevel.Info,
    tag = tag,
    loggable = loggable
)

@JvmName("warnString")
suspend fun Logger.warn(
    tag: String? = null,
    loggable: () -> String,
) = log(
    level = LogLevel.Warn,
    tag = tag,
    loggable = loggable,
)

@JvmName("errorString")
suspend fun Logger.error(
    tag: String? = null,
    loggable: () -> String,
) = log(
    level = LogLevel.Error,
    tag = tag,
    loggable = loggable
)

suspend fun Logger.log(
    level: LogLevel,
    tag: String? = null,
    loggable: () -> String,
) = log(
    level = level,
    tag = tag,
    loggable = { object : Loggable { override val message = loggable() } }
)

fun Logger.getDebugFlow(
    exclusive: Boolean = false,
): Flow<Loggable> = getLogFlow(level = LogLevel.Debug, exclusive = exclusive)

fun Logger.getInfoFlow(
    exclusive: Boolean = false,
): Flow<Loggable> = getLogFlow(level = LogLevel.Info, exclusive = exclusive)

fun Logger.getWarnFlow(
    exclusive: Boolean = false,
): Flow<Loggable> = getLogFlow(level = LogLevel.Warn, exclusive = exclusive)

fun Logger.getErrorFlow(
    exclusive: Boolean = false,
): Flow<Loggable> = getLogFlow(level = LogLevel.Error, exclusive = exclusive)
