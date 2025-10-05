package com.alexrdclement.uiplayground.theme

enum class FontStyle {
    Normal,
    Italic,
}

fun FontStyle.toComposeFontStyle(): androidx.compose.ui.text.font.FontStyle = when (this) {
    FontStyle.Normal -> androidx.compose.ui.text.font.FontStyle.Normal
    FontStyle.Italic -> androidx.compose.ui.text.font.FontStyle.Italic
}

fun androidx.compose.ui.text.font.FontStyle.toFontStyle(): FontStyle = when (this) {
    androidx.compose.ui.text.font.FontStyle.Normal -> FontStyle.Normal
    androidx.compose.ui.text.font.FontStyle.Italic -> FontStyle.Italic
    else -> FontStyle.Normal
}
