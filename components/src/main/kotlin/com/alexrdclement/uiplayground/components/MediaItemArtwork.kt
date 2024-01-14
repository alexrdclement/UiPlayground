package com.alexrdclement.uiplayground.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import coil.compose.AsyncImage

const val DisabledAlpha = 0.35f

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
                .aspectRatio(1f)
                .alpha(if (isEnabled) 1f else DisabledAlpha)
        )
    } else {
        // TODO: fallback image
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .aspectRatio(1f, matchHeightConstraintsFirst = true)
                .background(Color.Red)
                .clearAndSetSemantics {}
        ) {
            Text("Art")
        }
    }
}
