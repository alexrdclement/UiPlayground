package com.alexrdclement.uiplayground.components.datetime

import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.Padding

enum class DateFormat {
    YMD,
    YMDShort,
    MDY,
    MDYShort,
    MDYLong,
}

fun DateFormat.toFormat() = when (this) {
    DateFormat.YMD -> LocalDateFormatYMD
    DateFormat.YMDShort -> LocalDateFormatYMDShort
    DateFormat.MDY -> LocalDateFormatMDY
    DateFormat.MDYShort -> LocalDateFormatMDYShort
    DateFormat.MDYLong -> LocalDateFormatMDYLong
}

private const val BaseYear = 1960

val LocalDateFormatYMD = LocalDate.Format {
    year(Padding.ZERO)
    chars("-")
    monthNumber(Padding.ZERO)
    chars("-")
    day(Padding.ZERO)
}

val LocalDate.Formats.YMD: DateTimeFormat<LocalDate>
    get() = LocalDateFormatYMD

val LocalDateFormatYMDShort = LocalDate.Format {
    yearTwoDigits(baseYear = BaseYear)
    chars("-")
    monthNumber(Padding.ZERO)
    chars("-")
    day(Padding.ZERO)
}

val LocalDate.Formats.YMDShort: DateTimeFormat<LocalDate>
    get() = LocalDateFormatYMDShort

val LocalDateFormatMDY = LocalDate.Format {
    monthName(MonthNames.ENGLISH_ABBREVIATED)
    chars(" ")
    day(Padding.NONE)
    chars(", ")
    yearTwoDigits(baseYear = BaseYear)
}

val LocalDate.Formats.MDY: DateTimeFormat<LocalDate>
    get() = LocalDateFormatMDY

val LocalDateFormatMDYShort = LocalDate.Format {
    monthNumber(Padding.ZERO)
    chars("/")
    day(Padding.ZERO)
    chars("/")
    yearTwoDigits(baseYear = BaseYear)
}

val LocalDate.Formats.MDYShort: DateTimeFormat<LocalDate>
    get() = LocalDateFormatMDYShort

val LocalDateFormatMDYLong = LocalDate.Format {
    monthName(MonthNames.ENGLISH_FULL)
    chars(" ")
    day(Padding.NONE)
    chars(", ")
    year(Padding.NONE)
}

val LocalDate.Formats.MDYLong: DateTimeFormat<LocalDate>
    get() = LocalDateFormatMDYLong
