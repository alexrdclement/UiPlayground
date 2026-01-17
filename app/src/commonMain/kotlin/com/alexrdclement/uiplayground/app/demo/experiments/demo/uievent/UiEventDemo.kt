package com.alexrdclement.uiplayground.app.demo.experiments.demo.uievent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import com.alexrdclement.logging.Log
import com.alexrdclement.logging.LogLevel
import com.alexrdclement.logging.Logger
import com.alexrdclement.logging.LoggerImpl
import com.alexrdclement.logging.logString
import com.alexrdclement.palette.components.core.Text
import com.alexrdclement.palette.components.demo.Demo
import com.alexrdclement.palette.components.demo.control.Control
import com.alexrdclement.palette.components.layout.dialog.ErrorDialogContent
import com.alexrdclement.palette.components.util.mapSaverSafe
import com.alexrdclement.palette.theme.PaletteTheme
import com.alexrdclement.uiplayground.app.preview.UiPlaygroundPreview
import com.alexrdclement.uievent.UiEventState
import com.alexrdclement.uievent.collectAsState
import com.alexrdclement.uievent.toUiEvent
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun UiEventDemo(
    state: UiEventDemoState = rememberUiEventDemoState(),
    control: UiEventDemoControl = rememberUiEventDemoControl(state),
    modifier: Modifier = Modifier,
) {
    var error by rememberSaveable { mutableStateOf<String?>(null) }

    LaunchedEffect(state.error) {
        state.error.collect { message ->
            error = message
        }
    }

    Demo(
        controls = control.controls,
        modifier = modifier
            .fillMaxSize(),
    ) {
        val logs by state.logs.collectAsState(persistentListOf())

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(PaletteTheme.spacing.large)
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
                Text("Logs", style = PaletteTheme.typography.titleMedium)
            }
            items(
                items = logs,
            ) { log ->
                LogDisplay(
                    log = log,
                    modifier = Modifier
                        .padding(horizontal = PaletteTheme.spacing.medium)
                )
            }
        }
    }

    error?.let {
        Dialog(
            onDismissRequest = { error = null },
        ) {
            ErrorDialogContent(
                message = it,
                onDismissRequest = { error = null },
            )
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
        verticalArrangement = Arrangement.spacedBy(PaletteTheme.spacing.medium),
        modifier = modifier,
    ) {
        Text(level.name, style = PaletteTheme.typography.labelLarge)

        Column(
            verticalArrangement = Arrangement.spacedBy(PaletteTheme.spacing.small),
            modifier = Modifier
                .padding(horizontal = PaletteTheme.spacing.medium)
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

    val error = logger.getLogFlow(level = LogLevel.Error)
        .map { it.message }
        .toUiEvent(coroutineScope)

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
