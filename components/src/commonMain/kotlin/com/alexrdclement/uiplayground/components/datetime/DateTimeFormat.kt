package com.alexrdclement.uiplayground.components.datetime

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.format.DateTimeFormat

enum class DateTimeFormatToken {
    MDYContinental,
    MDYContinentalShort,
    YMDContinental,
    YMDContinentalShort,
    ContinentalMDY,
    ContinentalYMD,
}

fun DateTimeFormatToken.toFormat() = when (this) {
    DateTimeFormatToken.MDYContinental -> LocalDateTimeFormatMDYContinental
    DateTimeFormatToken.MDYContinentalShort -> LocalDateTimeFormatMDYContinentalShort
    DateTimeFormatToken.YMDContinental -> LocalDateTimeFormatYMDContinental
    DateTimeFormatToken.YMDContinentalShort -> LocalDateTimeFormatYMDContinentalShort
    DateTimeFormatToken.ContinentalMDY -> LocalDateTimeFormatContinentalMDY
    DateTimeFormatToken.ContinentalYMD -> LocalDateTimeFormatContinentalYMD
}

val LocalDateTimeFormatMDYContinental = buildLocalDateTimeFormat(
    date = LocalDate.Formats.MDY,
    time = LocalTime.Formats.HMContinental,
    order = DateTimeOrder.DateFirst,
)

val LocalDateTime.Formats.MDYContinental: DateTimeFormat<LocalDateTime>
    get() = LocalDateTimeFormatMDYContinental

val LocalDateTimeFormatMDYContinentalShort = buildLocalDateTimeFormat(
    date = LocalDate.Formats.MDYShort,
    time = LocalTime.Formats.HMContinental,
    order = DateTimeOrder.DateFirst,
)

val LocalDateTime.Formats.MDYContinentalShort: DateTimeFormat<LocalDateTime>
    get() = LocalDateTimeFormatMDYContinentalShort

val LocalDateTimeFormatYMDContinental = buildLocalDateTimeFormat(
    date = LocalDate.Formats.YMD,
    time = LocalTime.Formats.HMContinental,
    order = DateTimeOrder.DateFirst,
)

val LocalDateTime.Formats.YMDContinental: DateTimeFormat<LocalDateTime>
    get() = LocalDateTimeFormatYMDContinental

val LocalDateTimeFormatYMDContinentalShort = buildLocalDateTimeFormat(
    date = LocalDate.Formats.YMDShort,
    time = LocalTime.Formats.HMContinental,
    order = DateTimeOrder.DateFirst,
)

val LocalDateTime.Formats.YMDContinentalShort: DateTimeFormat<LocalDateTime>
    get() = LocalDateTimeFormatYMDContinentalShort

val LocalDateTimeFormatContinentalMDY = buildLocalDateTimeFormat(
    time = LocalTime.Formats.HMContinental,
    date = LocalDate.Formats.MDY,
    order = DateTimeOrder.TimeFirst,
)

val LocalDateTime.Formats.ContinentalMDY: DateTimeFormat<LocalDateTime>
    get() = LocalDateTimeFormatContinentalMDY

val LocalDateTimeFormatContinentalYMD = buildLocalDateTimeFormat(
    time = LocalTime.Formats.HMContinental,
    date = LocalDate.Formats.YMD,
    order = DateTimeOrder.TimeFirst,
)

val LocalDateTime.Formats.ContinentalYMD: DateTimeFormat<LocalDateTime>
    get() = LocalDateTimeFormatContinentalYMD

private enum class DateTimeOrder {
    DateFirst,
    TimeFirst,
}

private fun buildLocalDateTimeFormat(
    date: DateTimeFormat<LocalDate>,
    time: DateTimeFormat<LocalTime>,
    separator: String = " ",
    order: DateTimeOrder,
): DateTimeFormat<LocalDateTime> = LocalDateTime.Format {
    when (order) {
        DateTimeOrder.DateFirst -> {
            date(date)
            chars(separator)
            time(time)
        }
        DateTimeOrder.TimeFirst -> {
            time(time)
            chars(separator)
            date(date)
        }
    }
}
