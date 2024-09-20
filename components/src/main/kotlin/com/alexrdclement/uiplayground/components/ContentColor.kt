package com.alexrdclement.uiplayground.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import com.alexrdclement.uiplayground.theme.ColorScheme
import com.alexrdclement.uiplayground.theme.PlaygroundTheme

val LocalContentColor = compositionLocalOf { Color.Black }

fun ColorScheme.contentColorFor(backgroundColor: Color): Color =
    when (backgroundColor) {
        primary -> onPrimary
        secondary -> onSecondary
        background -> onBackground
        surface -> onSurface
        surfaceVariant -> onSurfaceVariant
        else -> Color.Unspecified
    }

@Composable
fun contentColorFor(backgroundColor: Color) =
    PlaygroundTheme.colorScheme.contentColorFor(backgroundColor).takeOrElse {
        LocalContentColor.current
    }
