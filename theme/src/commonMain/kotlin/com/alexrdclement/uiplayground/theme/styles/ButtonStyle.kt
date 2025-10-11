package com.alexrdclement.uiplayground.theme.styles

import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import com.alexrdclement.uiplayground.theme.ColorToken
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import com.alexrdclement.uiplayground.theme.ShapeToken
import com.alexrdclement.uiplayground.theme.toColor

enum class ButtonStyleToken {
    Primary,
    Secondary,
    Tertiary,
}

data class ButtonStyle(
    val token: ButtonStyleToken,
    val contentColor: ColorToken,
    val containerColor: ColorToken,
    val shape: ShapeToken,
    val border: Border?,
)

fun ButtonStyleToken.toStyle(buttonStyles: ButtonStyleScheme): ButtonStyle {
    return when (this) {
        ButtonStyleToken.Primary -> buttonStyles.primary
        ButtonStyleToken.Secondary -> buttonStyles.secondary
        ButtonStyleToken.Tertiary -> buttonStyles.tertiary
    }
}

@Composable
fun ButtonStyleToken.toStyle(): ButtonStyle {
    return toStyle(PlaygroundTheme.styles.buttonStyles)
}

data class Border(
    val width: Dp,
    val color: ColorToken,
)

val Border.stroke: BorderStroke?
    @Composable
    get() = BorderStroke(
        width = width,
        color = color.toColor(),
    )
