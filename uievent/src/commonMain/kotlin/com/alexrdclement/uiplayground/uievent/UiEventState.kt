package com.alexrdclement.uiplayground.uievent

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * A state holder for one-time UI events. Conceptually just a Flow but maintains state to guarantee
 * events are handled before they're cleared.
 *
 * Credit to @tevjef.
 */
class UiEventState<T>(
    initialValue: T? = null,
) {
    internal val state = MutableStateFlow(initialValue)

    suspend fun emit(value: T) = state.emit(value)

    fun tryEmit(value: T) = state.tryEmit(value)

    suspend fun emitAll(flow: Flow<T>) = state.emitAll(flow)

    suspend fun collect(action: suspend (T) -> Unit) {
        state.filterNotNull().collect {
            action(it)
            state.update { null }
        }
    }
}

suspend fun UiEventState<Unit?>.fire() = emit(Unit)

fun UiEventState<Unit?>.tryFire() = tryEmit(Unit)

@Composable
fun <T> UiEventState<T>.collectAsState(
    context: CoroutineContext = EmptyCoroutineContext,
) = state.collectAsState(context = context)
