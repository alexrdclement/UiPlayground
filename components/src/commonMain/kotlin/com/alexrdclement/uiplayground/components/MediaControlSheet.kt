@file:OptIn(ExperimentalFoundationApi::class)

package com.alexrdclement.uiplayground.components

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.gestures.snapTo
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.trace
import com.alexrdclement.uiplayground.components.model.MediaItem
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
) {
    internal var anchoredDraggableState = AnchoredDraggableState(
        initialValue = initialValue,
        confirmValueChange = {
            when (it) {
                MediaControlSheetAnchor.PartiallyExpanded,
                MediaControlSheetAnchor.Expanded -> true
            }
        },
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

    internal suspend fun settle(animationSpec: AnimationSpec<Float>) {
        anchoredDraggableState.settle(animationSpec)
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
        fun Saver(): Saver<MediaControlSheetState, MediaControlSheetAnchor> = Saver(
            save = { it.currentValue },
            restore = {
                MediaControlSheetState(
                    initialValue = it,
                )
            }
        )
    }
}

@Composable
fun rememberMediaControlSheetState(
    initialValue: MediaControlSheetAnchor = MediaControlSheetAnchor.PartiallyExpanded,
): MediaControlSheetState {
    return rememberSaveable(
        saver = MediaControlSheetState.Saver()
    ) {
        MediaControlSheetState(
            initialValue = initialValue,
        )
    }
}

/**
 * Copied and modified from Material3 ModalBottomSheet.android.kt
 */
@ExperimentalFoundationApi
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

    state.anchoredDraggableState.updateAnchors(newAnchors, state.targetValue)
}
