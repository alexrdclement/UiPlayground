package com.alexrdclement.uiplayground.app.demo.experiments.demo.uievent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.app.demo.Demo
import com.alexrdclement.uiplayground.app.demo.control.Control
import com.alexrdclement.uiplayground.app.preview.UiPlaygroundPreview
import com.alexrdclement.uiplayground.components.core.Text
import com.alexrdclement.uiplayground.components.util.mapSaverSafe
import com.alexrdclement.uiplayground.log.Log
import com.alexrdclement.uiplayground.log.LogLevel
import com.alexrdclement.uiplayground.log.Logger
import com.alexrdclement.uiplayground.log.LoggerImpl
import com.alexrdclement.uiplayground.log.logString
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import com.alexrdclement.uiplayground.uievent.UiEventState
import com.alexrdclement.uiplayground.uievent.collectAsState
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun UiEventDemo(
    state: UiEventDemoState = rememberUiEventDemoState(),
    control: UiEventDemoControl = rememberUiEventDemoControl(state),
    modifier: Modifier = Modifier,
) {
    Demo(
        controls = control.controls,
        modifier = modifier
            .fillMaxSize(),
    ) {
        val logs by state.logs.collectAsState(persistentListOf())

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(PlaygroundTheme.spacing.large)
        ) {
            items(
                count = state.eventsByLogLevel.size,
            ) { index ->
                val (level, eventState) = state.eventsByLogLevel.entries.elementAt(index)
                val countFlow = state.eventCountByLogLevel.entries.elementAt(index).value
                val count by countFlow.collectAsState(0)
                LogLevelDisplay(
                    level = level,
                    logs = eventState,
                    logCount = count,
                )
            }
            item {
                Text("Logs", style = PlaygroundTheme.typography.titleMedium)
            }
            items(
                items = logs,
            ) { log ->
                LogDisplay(
                    log = log,
                    modifier = Modifier
                        .padding(horizontal = PlaygroundTheme.spacing.medium)
                )
            }
        }
    }
}

@Composable
fun LogLevelDisplay(
    level: LogLevel,
    logs: UiEventState<Log>,
    logCount: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(PlaygroundTheme.spacing.medium),
        modifier = modifier,
    ) {
        Text(level.name, style = PlaygroundTheme.typography.labelLarge)

        Column(
            verticalArrangement = Arrangement.spacedBy(PlaygroundTheme.spacing.small),
            modifier = Modifier
                .padding(horizontal = PlaygroundTheme.spacing.medium)
        ) {
            val eventState by logs.collectAsState()
            Text("Event state: $eventState")
            Text("Event fired $logCount times")
        }
    }
}

@Composable
fun LogDisplay(
    log: String,
    modifier: Modifier = Modifier,
) {
    Text(log, modifier = modifier)
}

@Composable
fun rememberUiEventDemoState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) = rememberSaveable(
    saver = UiEventDemoStateSaver(
        coroutineScope = coroutineScope,
    ),
) {
    UiEventDemoState(
        coroutineScope = coroutineScope,
    )
}

@Stable
class UiEventDemoState(
    val coroutineScope: CoroutineScope,
    val logger: Logger = LoggerImpl(coroutineScope),
) {
    val eventsByLogLevel = LogLevel.entries.associateWith {
        UiEventState<Log>()
    }

    private val mutableEventCountByLogLevel = LogLevel.entries.associateWith {
        MutableStateFlow(0)
    }
    val eventCountByLogLevel: Map<LogLevel, Flow<Int>> = mutableEventCountByLogLevel

    private val mutableLogs = MutableStateFlow(persistentListOf<String>())
    val logs = mutableLogs.asStateFlow()

    init {
        eventsByLogLevel.entries.forEach { (level, state) ->
            val logs = logger.getLogFlow(level)
            coroutineScope.launch {
                state.emitAll(logs)
            }
            coroutineScope.launch {
                logs.collect { log ->
                    val message = "[${level}] ${log.message}"
                    mutableLogs.update { it.add(0, message) }
                }
            }
        }
        mutableEventCountByLogLevel.entries.forEach { (level, state) ->
            val eventState = eventsByLogLevel.getValue(level)
            coroutineScope.launch {
                eventState.collect {
                    state.update { it + 1 }
                }
            }
        }
    }

    fun reset() {
        mutableEventCountByLogLevel.forEach {
            it.value.update { 0 }
        }
        mutableLogs.value = persistentListOf()
    }
}

fun UiEventDemoStateSaver(
    coroutineScope: CoroutineScope,
) = mapSaverSafe(
    save = { value ->
        mapOf(
        )
    },
    restore = { map ->
        UiEventDemoState(
            coroutineScope = coroutineScope,
        )
    }
)

@Composable
fun rememberUiEventDemoControl(
    state: UiEventDemoState,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) = remember(state, coroutineScope) {
    UiEventDemoControl(state, coroutineScope)
}

@Stable
class UiEventDemoControl(
    val state: UiEventDemoState,
    val coroutineScope: CoroutineScope,
) {
    val resetControl = Control.Button(
        name = "Reset",
        onClick = {
            coroutineScope.launch {
                state.reset()
            }
        },
    )

    val logLevelControls = LogLevel.entries.map(::makeControlForLevel)

    val controls = persistentListOf<Control>(
        resetControl,
        *logLevelControls.toTypedArray(),
    )

    fun log(level: LogLevel, loggable: () -> String) {
        coroutineScope.launch {
            state.logger.logString(level = level, tag = "UiEventDemo", loggable)
        }
    }

    private fun makeControlForLevel(level: LogLevel) = Control.Button(
        name = "Fire $level",
        onClick = {
            coroutineScope.launch {
                log(level) { "Firing $level event" }
            }
        },
    )
}

@Preview
@Composable
fun UiEventDemoPreview() {
    UiPlaygroundPreview {
        UiEventDemo()
    }
}
