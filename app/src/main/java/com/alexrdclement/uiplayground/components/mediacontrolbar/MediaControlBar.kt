@file:OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)

package com.alexrdclement.uiplayground.components.mediacontrolbar

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.snapTo
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
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
    Hidden,
    PartiallyExpanded,
    Expanded,
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MediaControlBar(
    mediaItem: MediaItem,
    isPlaying: Boolean,
    onPlayPauseClick: () -> Unit,
    modifier: Modifier = Modifier,
    state: MediaControlBarState = rememberMediaControlBarState(),
    onClick: () -> Unit = {},
    minHeight: Dp = 64.dp,
) {
    BoxWithConstraints(
        modifier = modifier
    ) {
        val minHeightPx = with(LocalDensity.current) {
            minHeight.toPx()
        }
        val expandedHeightPx = constraints.maxHeight

        val scope = rememberCoroutineScope()

        val settleToDismiss: (velocity: Float) -> Unit = {
            scope.launch { state.settle(it) }
        }

        val transition = updateTransition(
            targetState = state.targetValue,
            label = "MediaControlBar Transition",
        )

        val artworkSize by transition.animateDp(
            targetValueByState = { targetValue ->
                when (targetValue) {
                    MediaControlBarAnchorState.Expanded -> maxWidth
                    MediaControlBarAnchorState.PartiallyExpanded,
                    MediaControlBarAnchorState.Hidden -> minHeight
                }
            },
            label = "artworkSize",
        )

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
                    enabled = state.isVisible,
                )
                .modalBottomSheetAnchors(
                    state = state,
                    minHeight = minHeightPx,
                    fullHeight = expandedHeightPx.toFloat(),
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        scope.launch {
                            when (state.currentValue) {
                                MediaControlBarAnchorState.Hidden -> state.partialExpand()
                                MediaControlBarAnchorState.PartiallyExpanded -> state.expand()
                                MediaControlBarAnchorState.Expanded -> state.partialExpand()
                            }
                        }
                        onClick()
                    }

            ) {
                MediaItemArtwork(
                    modifier = Modifier
                        .align(Alignment.Top)
                        .animateContentSize()
                        .size(artworkSize)
                )

                val metadataAlpha by transition.animateFloat(
                    targetValueByState = { targetValue ->
                        when (targetValue) {
                            MediaControlBarAnchorState.Hidden -> 0f
                            MediaControlBarAnchorState.PartiallyExpanded -> 1f
                            MediaControlBarAnchorState.Expanded -> 0f
                        }
                    },
                    label = "Metadata alpha"
                )
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                        .alpha(metadataAlpha)
                ) {
                    Text(
                        text = mediaItem.title,
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        modifier = Modifier
                            .basicMarquee()
                    )
                    Text(
                        text = mediaItem.artists.joinToString { it.name },
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        modifier = Modifier
                            .basicMarquee()
                    )
                }

                PlayPauseButton(
                    onClick = onPlayPauseClick,
                    isPlaying = isPlaying,
                    modifier = Modifier
                        .size(52.dp)
                        .padding(8.dp)
                        .alpha(if (state.isExpanded) 0f else 1f)
                )
            }

            val contentAlpha by transition.animateFloat(
                targetValueByState = { targetValue ->
                    when (targetValue) {
                        MediaControlBarAnchorState.Hidden -> 0f
                        MediaControlBarAnchorState.PartiallyExpanded -> 0f
                        MediaControlBarAnchorState.Expanded -> 1f
                    }
                },
                label = "contentAlpha"
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(contentAlpha)
            ) {
                Text(text = "Current state ${transition.currentState}")
                Text(text = "Target state ${transition.targetState}")
            }
        }
    }
}

@Composable
fun MediaItemArtwork(
    modifier: Modifier,
) {
    Box(
        modifier = modifier
            .aspectRatio(1f, matchHeightConstraintsFirst = false)
            .background(Color.Red)
    )
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
                MediaControlBarAnchorState.Hidden -> false // TODO: Temp
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

    suspend fun hide() {
        anchoredDraggableState.animateTo(MediaControlBarAnchorState.Hidden)
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

    val currentValue: MediaControlBarAnchorState get() = anchoredDraggableState.currentValue
    val targetValue: MediaControlBarAnchorState get() = anchoredDraggableState.targetValue

    val isVisible: Boolean get() = currentValue != MediaControlBarAnchorState.Hidden
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
    initialValue: MediaControlBarAnchorState = MediaControlBarAnchorState.Hidden,
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
        MediaControlBarAnchorState.Hidden at fullHeight
        if (size.height > (minHeight)) {
            MediaControlBarAnchorState.PartiallyExpanded at fullHeight - minHeight
        }
        if (size.height != 0) {
            MediaControlBarAnchorState.Expanded at max(0f, fullHeight - size.height)
        }
    }

    val newTarget = when (state.targetValue) {
        MediaControlBarAnchorState.Hidden -> MediaControlBarAnchorState.Hidden
        MediaControlBarAnchorState.PartiallyExpanded, MediaControlBarAnchorState.Expanded -> {
            val hasPartiallyExpandedState =
                newAnchors.hasAnchorFor(MediaControlBarAnchorState.PartiallyExpanded)
            when {
                hasPartiallyExpandedState -> MediaControlBarAnchorState.PartiallyExpanded
                newAnchors.hasAnchorFor(MediaControlBarAnchorState.Expanded) ->
                    MediaControlBarAnchorState.Expanded
                else -> MediaControlBarAnchorState.Hidden
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
        MediaControlBar(
            mediaItem = mediaItem,
            isPlaying = isPlaying,
            state = rememberMediaControlBarState(),
            onClick = { /*TODO*/ },
            onPlayPauseClick = { isPlaying = !isPlaying }
        )
    }
}
