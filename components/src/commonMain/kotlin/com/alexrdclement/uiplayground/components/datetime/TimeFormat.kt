package com.alexrdclement.uiplayground.components.datetime

import kotlinx.datetime.LocalTime
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.Padding

enum class TimeFormat {
    HMContinental,
    HMAmPmPadZero,
    HMSContinental,
    HMSAmPmPadZero,
}

fun TimeFormat.toFormat() = when (this) {
    TimeFormat.HMContinental -> LocalTimeFormatHMContinental
    TimeFormat.HMAmPmPadZero -> LocalTimeFormatHMAmPmPadZero
    TimeFormat.HMSContinental -> LocalTimeFormatHMSContinental
    TimeFormat.HMSAmPmPadZero -> LocalTimeFormatHMSAmPmPadZero
}

val LocalTimeFormatHMContinental = LocalTime.Format {
    hour(padding = Padding.ZERO)
    chars(":")
    minute(padding = Padding.ZERO)
}

val LocalTime.Formats.HMContinental: DateTimeFormat<LocalTime>
    get() = LocalTimeFormatHMContinental

val LocalTimeFormatHMAmPmPadZero = LocalTime.Format {
    amPmHour(padding = Padding.ZERO)
    chars(":")
    minute(padding = Padding.ZERO)
    chars(" ")
    amPmMarker(am = "am", pm = "pm")
}

val LocalTime.Formats.HMAmPmPadZero: DateTimeFormat<LocalTime>
    get() = LocalTimeFormatHMAmPmPadZero


val LocalTimeFormatHMSContinental = LocalTime.Format {
    hour(padding = Padding.ZERO)
    chars(":")
    minute(padding = Padding.ZERO)
    chars(":")
    second(padding = Padding.ZERO)
}

val LocalTime.Formats.HMSContinental: DateTimeFormat<LocalTime>
    get() = LocalTimeFormatHMSContinental

val LocalTimeFormatHMSAmPmPadZero = LocalTime.Format {
    amPmHour(padding = Padding.ZERO)
    chars(":")
    minute(padding = Padding.ZERO)
    chars(":")
    second(padding = Padding.ZERO)
    chars(" ")
    amPmMarker(am = "am", pm = "pm")
}

val LocalTime.Formats.HMSAmPmPadZero: DateTimeFormat<LocalTime>
    get() = LocalTimeFormatHMSAmPmPadZero
