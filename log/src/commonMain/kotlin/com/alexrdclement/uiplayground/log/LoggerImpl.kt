package com.alexrdclement.uiplayground.log

import Loggable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.merge

class LoggerImpl(
    extraBufferCapacity: Int = 100,
): Logger {
    private val debugFlow = MutableSharedFlow<Loggable>(extraBufferCapacity = extraBufferCapacity)
    private val infoFlow = MutableSharedFlow<Loggable>(extraBufferCapacity = extraBufferCapacity)
    private val warnFlow = MutableSharedFlow<Loggable>(extraBufferCapacity = extraBufferCapacity)
    private val errorFlow = MutableSharedFlow<Loggable>(extraBufferCapacity = extraBufferCapacity)

    override suspend fun log(level: LogLevel, tag: String?, loggable: () -> Loggable) {
        when (level) {
            LogLevel.Debug -> if (debugFlow.subscriptionCount.value > 0) debugFlow.emit(loggable())
            LogLevel.Info -> if (infoFlow.subscriptionCount.value > 0) infoFlow.emit(loggable())
            LogLevel.Warn -> if (warnFlow.subscriptionCount.value > 0) warnFlow.emit(loggable())
            LogLevel.Error -> if (errorFlow.subscriptionCount.value > 0) errorFlow.emit(loggable())
        }
    }

    override fun getLogFlow(
        level: LogLevel,
        exclusive: Boolean
    ): Flow<Loggable> {
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
}
