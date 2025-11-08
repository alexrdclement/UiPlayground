package com.alexrdclement.uiplayground.components.auth

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.components.core.Button
import com.alexrdclement.uiplayground.components.core.Text
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import com.alexrdclement.uiplayground.theme.styles.ButtonStyleToken
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

enum class AuthState {
    Loading,
    LoggedOut,
    LoggedIn,
}

enum class AuthButtonStyle {
    Primary,
    Secondary,
    Tertiary,
}

@Composable
fun AuthButton(
    authState: AuthState,
    style: AuthButtonStyle,
    onLogInClick: () -> Unit,
    onLogOutClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (authState) {
        AuthState.Loading -> AuthButtonText(
            authState = authState,
            style = style,
            modifier = modifier,
        )
        AuthState.LoggedOut -> LogInButton(
            style = style,
            onLogInClick = onLogInClick,
            modifier = modifier,
        )
        AuthState.LoggedIn -> LogOutButton(
            style = style,
            onLogOutClick = onLogOutClick,
            modifier = modifier,
        )
    }
}

@Composable
fun LogInButton(
    style: AuthButtonStyle,
    onLogInClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AuthButton(
        authState = AuthState.LoggedOut,
        style = style,
        onClick = onLogInClick,
        modifier = modifier,
    )
}

@Composable
fun LogOutButton(
    style: AuthButtonStyle,
    onLogOutClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AuthButton(
        authState = AuthState.LoggedIn,
        style = style,
        onClick = onLogOutClick,
        modifier = modifier,
    )
}

@Composable
fun AuthButton(
    authState: AuthState,
    style: AuthButtonStyle,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        style = when (style) {
            AuthButtonStyle.Primary -> ButtonStyleToken.Primary
            AuthButtonStyle.Secondary -> ButtonStyleToken.Secondary
            AuthButtonStyle.Tertiary -> ButtonStyleToken.Tertiary
        },
        onClick = onClick,
        modifier = modifier,
    ) {
        AuthButtonText(
            authState = authState,
            style = style,
        )
    }
}

@Composable
fun AuthButtonText(
    authState: AuthState,
    style: AuthButtonStyle,
    modifier: Modifier = Modifier,
) {
    Text(
        text = when (authState) {
            AuthState.Loading -> "..."
            AuthState.LoggedIn -> "Log out"
            AuthState.LoggedOut -> "Log in"
        },
        style = when (style) {
            AuthButtonStyle.Primary -> PlaygroundTheme.typography.labelLarge
            AuthButtonStyle.Secondary -> PlaygroundTheme.typography.labelSmall
            AuthButtonStyle.Tertiary -> PlaygroundTheme.typography.labelSmall
        },
        modifier = modifier,
    )
}

internal class AuthStatePreviewParameterProvider : PreviewParameterProvider<AuthState> {
    override val values = sequenceOf(AuthState.Loading, AuthState.LoggedOut, AuthState.LoggedIn)
}

internal class AuthButtonStylePreviewParameterProvider : PreviewParameterProvider<AuthButtonStyle> {
    override val values = sequenceOf(AuthButtonStyle.Primary, AuthButtonStyle.Secondary)
}

@Preview
@Composable
fun PreviewAuthButton(
    @PreviewParameter(AuthStatePreviewParameterProvider::class) authState: AuthState,
    @PreviewParameter(AuthButtonStylePreviewParameterProvider::class) buttonStyle: AuthButtonStyle,
) {
    PlaygroundTheme {
        AuthButton(
            authState = authState,
            style = buttonStyle,
            onLogInClick = {},
            onLogOutClick = {},
        )
    }
}
