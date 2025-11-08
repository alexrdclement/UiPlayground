package com.alexrdclement.uiplayground.components.datetime

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
val Instant.Companion.Formats: InstantFormats
    get() = InstantFormats

object InstantFormats {
    val MDYContinental = LocalDateTime.Formats.MDYContinental
    val MDYContinentalShort = LocalDateTime.Formats.MDYContinentalShort
    val YMDContinental = LocalDateTime.Formats.YMDContinental
    val YMDContinentalShort = LocalDateTime.Formats.YMDContinentalShort

    val ContinentalMDY = LocalDateTime.Formats.ContinentalMDY
    val ContinentalYMD = LocalDateTime.Formats.ContinentalYMD
}

@OptIn(ExperimentalTime::class)
fun Instant.format(format: DateTimeFormat<LocalDateTime>): String =
    format.format(this.toLocalDateTime(TimeZone.currentSystemDefault()))
