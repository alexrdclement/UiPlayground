package com.alexrdclement.uiplayground.components.mediacontrolsheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.util.UiPlaygroundPreview

@Composable
fun PlayPauseButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isPlaying: Boolean = false,
    isEnabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        enabled = isEnabled,
        contentPadding = PaddingValues(vertical = 2.dp, horizontal = 2.dp),
        modifier = modifier
            .aspectRatio(1f)
    ) {
        Image(
            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
            contentDescription = if (isPlaying) "Pause" else "Play",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Preview
@Composable
private fun Preview() {
    UiPlaygroundPreview {
        PlayPauseButton(
            isEnabled = true,
            isPlaying = false,
            onClick = {},
            modifier = Modifier.size(128.dp)
        )
    }
}