package com.alexrdclement.uiplayground.components.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.components.core.Button
import com.alexrdclement.uiplayground.components.core.Surface
import com.alexrdclement.uiplayground.components.core.Text
import com.alexrdclement.uiplayground.components.navigation.BackNavigationButton
import com.alexrdclement.uiplayground.components.util.copy
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import com.alexrdclement.uiplayground.theme.styles.ButtonStyleToken
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    navButton: @Composable (() -> Unit)? = null,
    actions: @Composable (() -> Unit)? = null,
    title: @Composable (() -> Unit)? = null,
) {
    val windowInsetsPaddingValues = WindowInsets.systemBars.asPaddingValues().copy(bottom = 0.dp)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(windowInsetsPaddingValues)
            .consumeWindowInsets(windowInsetsPaddingValues)
            .padding(vertical = PlaygroundTheme.spacing.small)
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.padding(start = PlaygroundTheme.spacing.small)) {
            navButton?.invoke()
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = PlaygroundTheme.spacing.small)
        ) {
            title?.invoke()
        }
        Box(modifier = Modifier.padding(end = PlaygroundTheme.spacing.small)) {
            actions?.invoke()
        }
    }
}

@Preview
@Composable
private fun Preview() {
    PlaygroundTheme {
        Surface {
            TopBar(
                title = {
                    Text("Title", style = PlaygroundTheme.typography.headline)
                }
            )
        }
    }
}

@Preview
@Composable
private fun NavButtonPreview() {
    PlaygroundTheme {
        Surface {
            TopBar(
                title = {
                    Text("Title", style = PlaygroundTheme.typography.headline)
                },
                navButton = {
                    BackNavigationButton(onClick = {})
                }
            )
        }
    }
}

@Preview
@Composable
private fun ActionsPreview() {
    PlaygroundTheme {
        Surface {
            TopBar(
                title = {
                    Text("Title", style = PlaygroundTheme.typography.headline)
                },
                actions = {
                    Button(
                        style = ButtonStyleToken.Secondary,
                        onClick = {},
                        modifier = Modifier.size(48.dp),
                    ) {
                        Text("Action")
                    }
                }
            )
        }
    }
}
