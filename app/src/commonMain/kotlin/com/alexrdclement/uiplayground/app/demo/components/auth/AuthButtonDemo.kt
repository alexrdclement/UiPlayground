package com.alexrdclement.uiplayground.app.demo.components.auth

import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.components.auth.AuthButton
import com.alexrdclement.uiplayground.components.auth.AuthButtonStyle
import com.alexrdclement.uiplayground.components.auth.AuthState
import com.alexrdclement.uiplayground.components.demo.Demo
import com.alexrdclement.uiplayground.components.demo.control.Control
import com.alexrdclement.uiplayground.components.demo.control.enumControl
import com.alexrdclement.uiplayground.components.util.mapSaverSafe
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import kotlinx.collections.immutable.persistentListOf

@Composable
fun AuthButtonDemo(
    state: AuthButtonDemoState = rememberAuthButtonDemoState(),
    control: AuthButtonDemoControl = rememberAuthButtonDemoControl(state),
    modifier: Modifier = Modifier,
) {
    Demo(
        controls = control.controls,
        modifier = modifier
            .fillMaxSize()
    ) {
        AuthButtonDemo(
            state = state,
            control = control,
        )
    }
}

@Composable
fun BoxWithConstraintsScope.AuthButtonDemo(
    modifier: Modifier = Modifier,
    state: AuthButtonDemoState = rememberAuthButtonDemoState(),
    control: AuthButtonDemoControl = rememberAuthButtonDemoControl(state),
) {
    AuthButton(
        authState = state.authState,
        style = state.style,
        onLogInClick = {},
        onLogOutClick = {},
        modifier = modifier
            .align(Alignment.Center)
            .padding(PlaygroundTheme.spacing.medium)
    )
}

@Composable
fun rememberAuthButtonDemoState(
    authStateInitial: AuthState = AuthState.LoggedOut,
    styleInitial: AuthButtonStyle = AuthButtonStyle.Secondary,
): AuthButtonDemoState = rememberSaveable(
    saver = AuthButtonDemoStateSaver,
) {
    AuthButtonDemoState(
        authStateInitial = authStateInitial,
        styleInitial = styleInitial,
    )
}

@Stable
class AuthButtonDemoState(
    authStateInitial: AuthState,
    styleInitial: AuthButtonStyle,
) {
    var authState by mutableStateOf(authStateInitial)
        internal set

    var style by mutableStateOf(styleInitial)
        internal set
}

private const val authStateKey = "authState"
private const val styleKey = "style"

val AuthButtonDemoStateSaver = mapSaverSafe(
    save = { value ->
        mapOf(
            authStateKey to value.authState,
            styleKey to value.style,
        )
    },
    restore = { map ->
        AuthButtonDemoState(
            authStateInitial = map[authStateKey] as AuthState,
            styleInitial = map[styleKey] as AuthButtonStyle,
        )
    },
)

@Composable
fun rememberAuthButtonDemoControl(
    state: AuthButtonDemoState,
): AuthButtonDemoControl = remember(state) { AuthButtonDemoControl(state) }

@Stable
class AuthButtonDemoControl(
    val state: AuthButtonDemoState,
) {
    val controls = persistentListOf<Control>(
        enumControl(
            name = "Auth state",
            values = { AuthState.entries },
            selectedValue = { state.authState },
            onValueChange = { state.authState = it },
        ),
        enumControl(
            name = "Style",
            values = { AuthButtonStyle.entries },
            selectedValue = { state.style },
            onValueChange = { state.style = it },
        ),
    )
}
