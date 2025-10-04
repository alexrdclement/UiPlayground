package com.alexrdclement.uiplayground.theme

enum class FontWeight {
    Thin,
    ExtraLight,
    Light,
    Normal,
    Medium,
    SemiBold,
    Bold,
    ExtraBold,
    Black,
}

fun FontWeight.toComposeFontWeight(): androidx.compose.ui.text.font.FontWeight {
    return when (this) {
        FontWeight.Thin -> androidx.compose.ui.text.font.FontWeight.Thin
        FontWeight.ExtraLight -> androidx.compose.ui.text.font.FontWeight.ExtraLight
        FontWeight.Light -> androidx.compose.ui.text.font.FontWeight.Light
        FontWeight.Normal -> androidx.compose.ui.text.font.FontWeight.Normal
        FontWeight.Medium -> androidx.compose.ui.text.font.FontWeight.Medium
        FontWeight.SemiBold -> androidx.compose.ui.text.font.FontWeight.SemiBold
        FontWeight.Bold -> androidx.compose.ui.text.font.FontWeight.Bold
        FontWeight.ExtraBold -> androidx.compose.ui.text.font.FontWeight.ExtraBold
        FontWeight.Black -> androidx.compose.ui.text.font.FontWeight.Black
    }
}

fun androidx.compose.ui.text.font.FontWeight.toFontWeight(): FontWeight {
    return when (this) {
        androidx.compose.ui.text.font.FontWeight.Thin -> FontWeight.Thin
        androidx.compose.ui.text.font.FontWeight.ExtraLight -> FontWeight.ExtraLight
        androidx.compose.ui.text.font.FontWeight.Light -> FontWeight.Light
        androidx.compose.ui.text.font.FontWeight.Normal -> FontWeight.Normal
        androidx.compose.ui.text.font.FontWeight.Medium -> FontWeight.Medium
        androidx.compose.ui.text.font.FontWeight.SemiBold -> FontWeight.SemiBold
        androidx.compose.ui.text.font.FontWeight.Bold -> FontWeight.Bold
        androidx.compose.ui.text.font.FontWeight.ExtraBold -> FontWeight.ExtraBold
        androidx.compose.ui.text.font.FontWeight.Black -> FontWeight.Black
        else -> throw IllegalArgumentException("Unknown FontWeight: $this")
    }
}
