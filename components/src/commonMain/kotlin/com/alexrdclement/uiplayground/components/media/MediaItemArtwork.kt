package com.alexrdclement.uiplayground.components.media

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import coil3.compose.AsyncImage
import com.alexrdclement.uiplayground.components.core.Text
import com.alexrdclement.uiplayground.theme.PlaygroundTheme

private const val DisabledAlpha = 0.35f

@Composable
fun MediaItemArtwork(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
) {
    if (imageUrl != null) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = modifier
                .alpha(if (isEnabled) 1f else DisabledAlpha)
        )
    } else {
        // TODO: fallback image
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .background(Color.Red)
                .clearAndSetSemantics {}
        ) {
            Text(
                "Art",
                style = PlaygroundTheme.typography.labelLarge
            )
        }
    }
}
