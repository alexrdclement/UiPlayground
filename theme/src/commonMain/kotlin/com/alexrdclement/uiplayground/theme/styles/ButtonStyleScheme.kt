package com.alexrdclement.uiplayground.theme.styles

import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.theme.ColorToken
import com.alexrdclement.uiplayground.theme.ShapeToken

data class ButtonStyleScheme(
    val primary: ButtonStyle,
    val secondary: ButtonStyle,
    val tertiary: ButtonStyle,
)

fun ButtonStyleScheme.copy(
    token: ButtonStyleToken,
    value: ButtonStyle,
) = this.copy(
    primary = if (token == ButtonStyleToken.Primary) value else this.primary,
    secondary = if (token == ButtonStyleToken.Secondary) value else this.secondary,
    tertiary = if (token == ButtonStyleToken.Tertiary) value else this.tertiary,
)

val PlaygroundButtonStyleScheme = ButtonStyleScheme(
    primary = ButtonStyle(
        token = ButtonStyleToken.Primary,
        contentColor = ColorToken.Primary,
        containerColor = ColorToken.Surface,
        shape = ShapeToken.Primary,
        border = Border(1.dp, ColorToken.Outline),
    ),
    secondary = ButtonStyle(
        token = ButtonStyleToken.Secondary,
        contentColor = ColorToken.Secondary,
        containerColor = ColorToken.Surface,
        shape = ShapeToken.Secondary,
        border = Border(1.dp, ColorToken.Outline),
    ),
    tertiary = ButtonStyle(
        token = ButtonStyleToken.Tertiary,
        contentColor = ColorToken.OnPrimary,
        containerColor = ColorToken.Primary,
        shape = ShapeToken.Tertiary,
        border = null,
    ),
)
