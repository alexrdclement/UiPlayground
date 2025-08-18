package com.alexrdclement.uiplayground.app.configuration

import androidx.compose.foundation.Indication
import androidx.compose.runtime.Composable
import com.alexrdclement.uiplayground.theme.Typography

interface ConfigurationState {
    val colorMode: ColorMode
    val typography: Typography
    val indication: Indication
}

interface ConfigurationController : ConfigurationState {
    fun setColorMode(colorMode: ColorMode): Boolean
    fun setTypography(typography: Typography): Boolean
    fun setIndication(indication: Indication): Boolean
}

@Composable
expect fun rememberConfigurationController(): ConfigurationController
