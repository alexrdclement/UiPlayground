@file:OptIn(ExperimentalTime::class)

package com.alexrdclement.uiplayground.app.demo.components.datetime

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alexrdclement.uiplayground.components.core.Text
import com.alexrdclement.uiplayground.components.datetime.DateFormat
import com.alexrdclement.uiplayground.components.datetime.DateTimeFormatToken
import com.alexrdclement.uiplayground.components.datetime.TimeFormat
import com.alexrdclement.uiplayground.components.datetime.toFormat
import com.alexrdclement.uiplayground.components.demo.Demo
import com.alexrdclement.uiplayground.components.demo.control.Control
import com.alexrdclement.uiplayground.components.demo.control.enumControl
import com.alexrdclement.uiplayground.components.util.mapSaverSafe
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Composable
fun DateTimeDemo(
    state: DateTimeDemoState = rememberDateTimeDemoState(),
    control: DateTimeDemoControl = rememberDateTimeDemoControl(state),
    modifier: Modifier = Modifier,
) {
    Demo(
        controls = control.controls,
        modifier = modifier
            .fillMaxSize()
    ) {
        DateTimeDemo(
            state = state,
            control = control,
        )
    }
}

@Composable
fun BoxWithConstraintsScope.DateTimeDemo(
    modifier: Modifier = Modifier,
    state: DateTimeDemoState = rememberDateTimeDemoState(),
    control: DateTimeDemoControl = rememberDateTimeDemoControl(state),
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = PlaygroundTheme.spacing.medium,
            alignment = Alignment.CenterVertically,
        ),
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .align(Alignment.Center)
    ) {
        val timeFormatted by state.timeFormatted.collectAsStateWithLifecycle("")
        val dateFormatted by state.dateFormatted.collectAsStateWithLifecycle("")
        val dateTimeFormatted by state.dateTimeFormatted.collectAsStateWithLifecycle("")
        FormatDemo(
            title = "Time",
            demo = timeFormatted,
        )
        FormatDemo(
            title = "Date",
            demo = dateFormatted,
        )
        FormatDemo(
            title = "DateTime",
            demo = dateTimeFormatted,
        )
    }
}

@Composable
private fun FormatDemo(
    title: String,
    demo: String,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(PlaygroundTheme.spacing.medium),
        modifier = modifier
    ) {
        Text(
            text = title,
            style = PlaygroundTheme.typography.labelSmall,
            modifier = Modifier
                .border(1.dp, PlaygroundTheme.colorScheme.outline)
                .padding(PlaygroundTheme.spacing.xs)
        )
        Text(
            text = demo,
            style = PlaygroundTheme.typography.titleLarge,
        )
    }
}

@Composable
fun rememberDateTimeDemoState(
    timeZone: TimeZone = TimeZone.currentSystemDefault(),
    timeFormatInitial: TimeFormat = TimeFormat.HMSContinental,
    dateFormatInitial: DateFormat = DateFormat.YMD,
    dateTimeFormatInitial: DateTimeFormatToken = DateTimeFormatToken.YMDContinental,
): DateTimeDemoState = rememberSaveable(
    saver = DateTimeDemoStateSaver,
) {
    DateTimeDemoState(
        timeZoneInitial = timeZone,
        timeFormatInitial = timeFormatInitial,
        dateFormatInitial = dateFormatInitial,
        dateTimeFormatInitial = dateTimeFormatInitial,
    )
}

@Stable
class DateTimeDemoState(
    timeZoneInitial: TimeZone,
    timeFormatInitial: TimeFormat,
    dateFormatInitial: DateFormat,
    dateTimeFormatInitial: DateTimeFormatToken
) {
    val availableTimeZoneIds = TimeZone.availableZoneIds.toPersistentList()

    var timeZone by mutableStateOf(timeZoneInitial)
        internal set

    var timeFormat by mutableStateOf(timeFormatInitial)
        internal set

    var dateFormat by mutableStateOf(dateFormatInitial)
        internal set

    var dateTimeFormat by mutableStateOf(dateTimeFormatInitial)
        internal set

    val instant: Flow<Instant> = flow {
        while (true) {
            emit(Clock.System.now())
            delay(1.seconds)
        }
    }

    val localDateTime = instant.map { it.toLocalDateTime(timeZone) }

    val timeFormatted = localDateTime.map { it.time.format(timeFormat.toFormat()) }

    val dateFormatted = localDateTime.map { it.date.format(dateFormat.toFormat()) }

    val dateTimeFormatted = localDateTime.map { it.format(dateTimeFormat.toFormat()) }
}

private const val timeZoneKey = "timeZone"
private const val timeFormatKey = "timeFormat"
private const val dateFormatKey = "dateFormat"
private const val dateTimeFormatKey = "dateTimeFormat"

val DateTimeDemoStateSaver = mapSaverSafe(
    save = { value ->
        mapOf(
            timeZoneKey to value.timeZone.id,
            timeFormatKey to value.timeFormat,
            dateFormatKey to value.dateFormat,
            dateTimeFormatKey to value.dateTimeFormat,
        )
    },
    restore = { map ->
        DateTimeDemoState(
            timeZoneInitial = TimeZone.of(map[timeZoneKey] as String),
            timeFormatInitial = map[timeFormatKey] as TimeFormat,
            dateFormatInitial = map[dateFormatKey] as DateFormat,
            dateTimeFormatInitial = map[dateTimeFormatKey] as DateTimeFormatToken,
        )
    },
)

@Composable
fun rememberDateTimeDemoControl(
    state: DateTimeDemoState,
): DateTimeDemoControl = remember(state) { DateTimeDemoControl(state) }

@Stable
class DateTimeDemoControl(
    val state: DateTimeDemoState,
) {
    val controls = persistentListOf<Control>(
        enumControl(
            name = "Time format",
            values = { TimeFormat.entries },
            selectedValue = { state.timeFormat },
            onValueChange = { state.timeFormat = it },
        ),
        enumControl(
            name = "Date format",
            values = { DateFormat.entries },
            selectedValue = { state.dateFormat },
            onValueChange = { state.dateFormat = it },
        ),
        enumControl(
            name = "DateTime format",
            values = { DateTimeFormatToken.entries },
            selectedValue = { state.dateTimeFormat },
            onValueChange = { state.dateTimeFormat = it },
        ),
        Control.Dropdown(
            name = "Time zone",
            values = { state.availableTimeZoneIds.map { Control.Dropdown.DropdownItem(it, it) }.toPersistentList() },
            selectedIndex = { state.availableTimeZoneIds.indexOf(state.timeZone.id) },
            onValueChange = { index ->
                state.timeZone = TimeZone.of(state.availableTimeZoneIds[index])
            },
        ),
    )
}
