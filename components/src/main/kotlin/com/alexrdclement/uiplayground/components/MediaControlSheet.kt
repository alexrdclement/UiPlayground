@file:OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)

package com.alexrdclement.uiplayground.components

import androidx.compose.animation.core.exponentialDecay
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.gestures.snapTo
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.tracing.trace
import com.alexrdclement.uiplayground.components.model.Artist
import com.alexrdclement.uiplayground.components.model.MediaItem
import kotlinx.coroutines.launch
import kotlin.math.max

private const val TraceName = "MediaControlSheet"

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MediaControlSheet(
    mediaItem: MediaItem,
    isPlaying: Boolean,
    onPlayPauseClick: () -> Unit,
    onControlBarClick: () -> Unit,
    modifier: Modifier = Modifier,
    state: MediaControlSheetState = rememberMediaControlSheetState(),
    minHeight: Dp = 64.dp,
    content: @Composable () -> Unit = {},
) {
    trace(TraceName) {
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
                    stateDescription = when (state.currentValue) {
                        MediaControlSheetAnchor.PartiallyExpanded ->
                            MediaControlBarStateDescriptionPartiallyExpanded
                        MediaControlSheetAnchor.Expanded -> MediaControlBarStateDescriptionExpanded
                    }
                )

                content()
            }
        }
    }
}

enum class MediaControlSheetAnchor {
    PartiallyExpanded,
    Expanded,
}

@Stable
class MediaControlSheetState(
    val initialValue: MediaControlSheetAnchor,
    val density: Density,
) {
    internal var anchoredDraggableState = AnchoredDraggableState(
        initialValue = initialValue,
        snapAnimationSpec = spring(),
        decayAnimationSpec = exponentialDecay(),
        confirmValueChange = {
            when (it) {
                MediaControlSheetAnchor.PartiallyExpanded,
                MediaControlSheetAnchor.Expanded -> true
            }
        },
        positionalThreshold = { with(density) { 56.dp.toPx() } },
        velocityThreshold = { with(density) { 125.dp.toPx() } }
    )

    suspend fun expand() {
        anchoredDraggableState.animateTo(MediaControlSheetAnchor.Expanded)
    }

    suspend fun partialExpand() {
        anchoredDraggableState.animateTo(MediaControlSheetAnchor.PartiallyExpanded)
    }

    internal suspend fun animateTo(
        targetValue: MediaControlSheetAnchor,
    ) {
        anchoredDraggableState.animateTo(targetValue)
    }

    internal suspend fun snapTo(targetValue: MediaControlSheetAnchor) {
        anchoredDraggableState.snapTo(targetValue)
    }

    internal suspend fun settle(velocity: Float) {
        anchoredDraggableState.settle(velocity)
    }

    val offset: Float get() = anchoredDraggableState.offset

    val partialToFullProgress: Float get() {
        return anchoredDraggableState.progress(
            MediaControlSheetAnchor.PartiallyExpanded,
            MediaControlSheetAnchor.Expanded,
        )
    }

    val currentValue: MediaControlSheetAnchor get() = anchoredDraggableState.currentValue
    val targetValue: MediaControlSheetAnchor get() = anchoredDraggableState.targetValue

    val isExpanded: Boolean get() = targetValue == MediaControlSheetAnchor.Expanded

    companion object {
        fun Saver(
            density: Density,
        ): Saver<MediaControlSheetState, MediaControlSheetAnchor> = Saver(
            save = { it.currentValue },
            restore = {
                MediaControlSheetState(
                    initialValue = it,
                    density = density,
                )
            }
        )
    }
}

@Composable
fun rememberMediaControlSheetState(
    initialValue: MediaControlSheetAnchor = MediaControlSheetAnchor.PartiallyExpanded,
): MediaControlSheetState {
    val density = LocalDensity.current
    return rememberSaveable(
        saver = MediaControlSheetState.Saver(
            density = density,
        )
    ) {
        MediaControlSheetState(
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
    state: MediaControlSheetState,
    minHeight: Float,
    fullHeight: Float
) = onSizeChanged { size ->
    val newAnchors = DraggableAnchors {
        if (size.height > (minHeight)) {
            MediaControlSheetAnchor.PartiallyExpanded at fullHeight - minHeight
        }
        if (size.height != 0) {
            MediaControlSheetAnchor.Expanded at max(0f, fullHeight - size.height)
        }
    }

    val newTarget = when (state.targetValue) {
        MediaControlSheetAnchor.PartiallyExpanded -> {
            if (newAnchors.hasAnchorFor(MediaControlSheetAnchor.PartiallyExpanded)) {
                MediaControlSheetAnchor.PartiallyExpanded
            } else {
                MediaControlSheetAnchor.Expanded
            }
        }
        MediaControlSheetAnchor.Expanded -> {
            if (newAnchors.hasAnchorFor(MediaControlSheetAnchor.Expanded)) {
                MediaControlSheetAnchor.Expanded
            } else {
                MediaControlSheetAnchor.PartiallyExpanded
            }
        }
    }

    state.anchoredDraggableState.updateAnchors(newAnchors, newTarget)
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    val mediaItem = MediaItem(
        artworkThumbnailUrl = null,
        artworkLargeUrl = null,
        title = "Title",
        artists = listOf(Artist("Artist 1"), Artist("Artist 2")),
    )
    var isPlaying by remember { mutableStateOf(false) }
    val state = rememberMediaControlSheetState(
        initialValue = MediaControlSheetAnchor.PartiallyExpanded,
    )
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
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize(),
        ) {
            Text("Content")
        }
    }
}
