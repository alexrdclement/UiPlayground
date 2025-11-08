package com.alexrdclement.uiplayground.components.datetime

import kotlinx.datetime.LocalTime
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.Padding

val LocalTimeFormatContinental = LocalTime.Format {
    hour(padding = Padding.ZERO)
    chars(":")
    minute(padding = Padding.ZERO)
}

val LocalTime.Formats.Continental: DateTimeFormat<LocalTime>
    get() = LocalTimeFormatContinental

val LocalTimeFormatAmPmPadZero = LocalTime.Format {
    amPmHour(padding = Padding.ZERO)
    chars(":")
    minute(padding = Padding.ZERO)
}

val LocalTime.Formats.AmPmPadZero: DateTimeFormat<LocalTime>
    get() = LocalTimeFormatAmPmPadZero
