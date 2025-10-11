package com.alexrdclement.uiplayground.theme

import com.alexrdclement.uiplayground.theme.styles.ButtonStyleScheme
import com.alexrdclement.uiplayground.theme.styles.PlaygroundButtonStyleScheme

data class Styles(
    val buttonStyles: ButtonStyleScheme,
)

val PlaygroundStyles = Styles(
    buttonStyles = PlaygroundButtonStyleScheme,
)
