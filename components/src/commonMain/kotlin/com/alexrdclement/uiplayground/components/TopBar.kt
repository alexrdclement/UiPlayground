package com.alexrdclement.uiplayground.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.theme.PlaygroundTheme

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    title: @Composable (() -> Unit)? = null,
    navButton: @Composable (() -> Unit)? = null,
    actions: @Composable (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
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
