package com.alexrdclement.uiplayground.theme

enum class FontFamily {
    Default,
    Monospace,
    SansSerif,
    Serif,
    Cursive,
}

fun FontFamily.toComposeFontFamily(): androidx.compose.ui.text.font.FontFamily {
    return when (this) {
        FontFamily.Default -> androidx.compose.ui.text.font.FontFamily.Default
        FontFamily.Monospace -> androidx.compose.ui.text.font.FontFamily.Monospace
        FontFamily.SansSerif -> androidx.compose.ui.text.font.FontFamily.SansSerif
        FontFamily.Serif -> androidx.compose.ui.text.font.FontFamily.Serif
        FontFamily.Cursive -> androidx.compose.ui.text.font.FontFamily.Cursive
    }
}

fun androidx.compose.ui.text.font.FontFamily.toFontFamily(): FontFamily {
    return when (this) {
        androidx.compose.ui.text.font.FontFamily.Default -> FontFamily.Default
        androidx.compose.ui.text.font.FontFamily.Monospace -> FontFamily.Monospace
        androidx.compose.ui.text.font.FontFamily.SansSerif -> FontFamily.SansSerif
        androidx.compose.ui.text.font.FontFamily.Serif -> FontFamily.Serif
        androidx.compose.ui.text.font.FontFamily.Cursive -> FontFamily.Cursive
        else -> throw IllegalArgumentException("Unknown FontFamily: $this")
    }
}
