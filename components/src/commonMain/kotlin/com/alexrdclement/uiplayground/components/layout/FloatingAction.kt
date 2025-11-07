package com.alexrdclement.uiplayground.components.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.components.util.copy
import com.alexrdclement.uiplayground.theme.PlaygroundTheme

@Composable
fun FloatingAction(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .padding(
                WindowInsets.safeDrawing.asPaddingValues().copy(top = 0.dp)
            )
            .padding(vertical = PlaygroundTheme.spacing.small)
    ) {
        content()
    }
}
