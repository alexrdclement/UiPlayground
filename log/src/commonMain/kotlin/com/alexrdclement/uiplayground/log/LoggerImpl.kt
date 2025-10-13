package com.alexrdclement.uiplayground.log

import com.alexrdclement.uiplayground.loggable.Loggable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch

class LoggerImpl(
    private val coroutineScope: CoroutineScope,
    extraBufferCapacity: Int = 100,
): Logger {
    private val debugFlow = MutableSharedFlow<Log>(extraBufferCapacity = extraBufferCapacity)
    private val infoFlow = MutableSharedFlow<Log>(extraBufferCapacity = extraBufferCapacity)
    private val warnFlow = MutableSharedFlow<Log>(extraBufferCapacity = extraBufferCapacity)
    private val errorFlow = MutableSharedFlow<Log>(extraBufferCapacity = extraBufferCapacity)

    override fun log(level: LogLevel, tag: String?, loggable: () -> Loggable) {
        coroutineScope.launch {
            logSuspend(
                level = level,
                tag = tag,
                loggable = loggable,
            )
        }
    }

    override fun getLogFlow(
        level: LogLevel,
        exclusive: Boolean
    ): Flow<Log> {
        if (exclusive) {
            return when (level) {
                LogLevel.Debug -> debugFlow
                LogLevel.Info -> infoFlow
                LogLevel.Warn -> warnFlow
                LogLevel.Error -> errorFlow
            }
        }
        return when (level) {
            LogLevel.Debug -> merge(debugFlow, infoFlow, warnFlow, errorFlow)
            LogLevel.Info -> merge(infoFlow, warnFlow, errorFlow)
            LogLevel.Warn -> merge(warnFlow, errorFlow)
            LogLevel.Error -> errorFlow
        }
    }

    private suspend fun logSuspend(level: LogLevel, tag: String?, loggable: () -> Loggable) {
        when (level) {
            LogLevel.Debug -> if (debugFlow.subscriptionCount.value > 0) {
                debugFlow.emit(Log(level, tag, loggable()))
            }
            LogLevel.Info -> if (infoFlow.subscriptionCount.value > 0) {
                infoFlow.emit(Log(level, tag, loggable()))
            }
            LogLevel.Warn -> if (warnFlow.subscriptionCount.value > 0) {
                warnFlow.emit(Log(level, tag, loggable()))
            }
            LogLevel.Error -> if (errorFlow.subscriptionCount.value > 0) {
                errorFlow.emit(Log(level, tag, loggable()))
            }
        }
    }
}
