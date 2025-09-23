package com.alexrdclement.uiplayground.theme.control

import androidx.compose.foundation.Indication
import androidx.compose.runtime.Composable
import com.alexrdclement.uiplayground.theme.Typography

class ThemeControl internal constructor(
    private val state: ThemeStateImpl
): ThemeState by state {

    fun setTypography(typography: Typography): Boolean {
        state.typography = typography
        return true
    }

    fun setIndication(indication: Indication): Boolean {
        state.indication = indication
        return true
    }

}

@Composable
fun rememberThemeControl(): ThemeControl {
    val state: ThemeStateImpl = rememberThemeState()
    return ThemeControl(state)
}
