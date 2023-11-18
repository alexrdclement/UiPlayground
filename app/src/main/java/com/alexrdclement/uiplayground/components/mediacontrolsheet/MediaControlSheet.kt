@file:OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)

package com.alexrdclement.uiplayground.components.mediacontrolsheet

import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.gestures.snapTo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.util.UiPlaygroundPreview
import kotlinx.coroutines.launch
import kotlin.math.max

data class Artist(val name: String)

data class MediaItem(
    val title: String,
    val artists: List<Artist>,
)

enum class MediaControlBarAnchorState {
    PartiallyExpanded,
    Expanded,
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MediaControlSheet(
    mediaItem: MediaItem,
    isPlaying: Boolean,
    onPlayPauseClick: () -> Unit,
    onControlBarClick: () -> Unit,
    modifier: Modifier = Modifier,
    state: MediaControlBarState = rememberMediaControlBarState(),
    minHeight: Dp = 64.dp,
    content: @Composable () -> Unit = {},
) {
    BoxWithConstraints(
        modifier = modifier
    ) {
        val minHeightPx = with(LocalDensity.current) {
            minHeight.toPx()
        }
        val expandedHeightPx = constraints.maxHeight

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .offset {
                    IntOffset(
                        0,
                        state.offset.toInt()
                    )
                }
                .anchoredDraggable(
                    state = state.anchoredDraggableState,
                    orientation = Orientation.Vertical,
                )
                .modalBottomSheetAnchors(
                    state = state,
                    minHeight = minHeightPx,
                    fullHeight = expandedHeightPx.toFloat(),
                )
        ) {
            MediaControlBar(
                mediaItem = mediaItem,
                isPlaying = isPlaying,
                onPlayPauseClick = onPlayPauseClick,
                onClick = onControlBarClick,
                progress = { state.partialToFullProgress },
            )

            content()
        }
    }
}

@Composable
fun MediaItemArtwork(
    modifier: Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .aspectRatio(1f, matchHeightConstraintsFirst = true)
            .background(Color.Red)
    ) {
        Text("Hi")
    }
}

@Stable
class MediaControlBarState(
    val initialValue: MediaControlBarAnchorState,
    val density: Density,
) {

    internal var anchoredDraggableState = AnchoredDraggableState(
        initialValue = initialValue,
        animationSpec = spring(),
        confirmValueChange = {
            when (it) {
                MediaControlBarAnchorState.PartiallyExpanded,
                MediaControlBarAnchorState.Expanded -> true
            }
        },
        positionalThreshold = { with(density) { 56.dp.toPx() } },
        velocityThreshold = { with(density) { 125.dp.toPx() } }
    )

    suspend fun expand() {
        anchoredDraggableState.animateTo(MediaControlBarAnchorState.Expanded)
    }

    suspend fun partialExpand() {
        anchoredDraggableState.animateTo(MediaControlBarAnchorState.PartiallyExpanded)
    }

    internal suspend fun animateTo(
        targetValue: MediaControlBarAnchorState,
        velocity: Float = anchoredDraggableState.lastVelocity
    ) {
        anchoredDraggableState.animateTo(targetValue, velocity)
    }

    internal suspend fun snapTo(targetValue: MediaControlBarAnchorState) {
        anchoredDraggableState.snapTo(targetValue)
    }

    internal suspend fun settle(velocity: Float) {
        anchoredDraggableState.settle(velocity)
    }

    val offset: Float get() = anchoredDraggableState.offset

    val progress: Float get() = anchoredDraggableState.progress

    val partialToFullProgress: Float get() = when (currentValue) {
        MediaControlBarAnchorState.PartiallyExpanded -> when (targetValue) {
            MediaControlBarAnchorState.PartiallyExpanded -> {
                if (progress >= 1f) 0f else progress
            }

            MediaControlBarAnchorState.Expanded -> {
                if (progress >= 1f) 1f else progress
            }
        }

        MediaControlBarAnchorState.Expanded -> when (targetValue) {
            MediaControlBarAnchorState.PartiallyExpanded -> {
                if (progress >= 1f) 0f else 1f - progress
            }

            MediaControlBarAnchorState.Expanded -> {
                if (progress >= 1f) 1f else 1f - progress
            }
        }
    }

    val currentValue: MediaControlBarAnchorState get() = anchoredDraggableState.currentValue
    val targetValue: MediaControlBarAnchorState get() = anchoredDraggableState.targetValue

    val isExpanded: Boolean get() = targetValue == MediaControlBarAnchorState.Expanded

    companion object {
        fun Saver(
            density: Density,
        ): Saver<MediaControlBarState, MediaControlBarAnchorState> = Saver(
            save = { it.currentValue },
            restore = {
                MediaControlBarState(
                    initialValue = it,
                    density = density,
                )
            }
        )
    }
}

@Composable
fun rememberMediaControlBarState(
    initialValue: MediaControlBarAnchorState = MediaControlBarAnchorState.PartiallyExpanded,
): MediaControlBarState {
    val density = LocalDensity.current
    return rememberSaveable(
        saver = MediaControlBarState.Saver(
            density = density,
        )
    ) {
        MediaControlBarState(
            initialValue = initialValue,
            density = density,
        )
    }
}

/**
 * Copied and modified from Material3 ModalBottomSheet.android.kt
 */
@ExperimentalMaterial3Api
private fun Modifier.modalBottomSheetAnchors(
    state: MediaControlBarState,
    minHeight: Float,
    fullHeight: Float
) = onSizeChanged { size ->

    val newAnchors = DraggableAnchors {
        if (size.height > (minHeight)) {
            MediaControlBarAnchorState.PartiallyExpanded at fullHeight - minHeight
        }
        if (size.height != 0) {
            MediaControlBarAnchorState.Expanded at max(0f, fullHeight - size.height)
        }
    }

    val newTarget = when (state.targetValue) {
        MediaControlBarAnchorState.PartiallyExpanded, MediaControlBarAnchorState.Expanded -> {
            val hasPartiallyExpandedState =
                newAnchors.hasAnchorFor(MediaControlBarAnchorState.PartiallyExpanded)
            when {
                hasPartiallyExpandedState -> MediaControlBarAnchorState.PartiallyExpanded
                newAnchors.hasAnchorFor(MediaControlBarAnchorState.Expanded) ->
                    MediaControlBarAnchorState.Expanded
                else -> MediaControlBarAnchorState.PartiallyExpanded
            }
        }
    }

    state.anchoredDraggableState.updateAnchors(newAnchors, newTarget)
}

@Preview
@Composable
private fun Preview() {
    UiPlaygroundPreview {
        val mediaItem = MediaItem(
            title = "Title",
            artists = listOf(Artist("Artist 1"), Artist("Artist 2")),
        )
        var isPlaying by remember { mutableStateOf(false) }
        val state = rememberMediaControlBarState()
        val coroutineScope = rememberCoroutineScope()
        MediaControlSheet(
            mediaItem = mediaItem,
            isPlaying = isPlaying,
            onPlayPauseClick = { isPlaying = !isPlaying },
            onControlBarClick = {
                coroutineScope.launch {
                    state.expand()
                }
            },
            state = state,
        )
    }
}
