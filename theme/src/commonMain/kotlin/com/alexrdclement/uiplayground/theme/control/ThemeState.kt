package com.alexrdclement.uiplayground.theme.control

import androidx.compose.foundation.Indication
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.alexrdclement.uiplayground.theme.PlaygroundIndication
import com.alexrdclement.uiplayground.theme.PlaygroundTypography
import com.alexrdclement.uiplayground.theme.Typography

interface ThemeState {
    val typography: Typography
    val indication: Indication
}

internal class ThemeStateImpl(
    typographyInitial: Typography = PlaygroundTypography,
    indicationInitial: Indication = PlaygroundIndication,
) : ThemeState {
    override var typography by mutableStateOf(typographyInitial)
    override var indication by mutableStateOf(indicationInitial)
}

@Composable
internal fun rememberThemeState(): ThemeStateImpl {
    return ThemeStateImpl()
}
