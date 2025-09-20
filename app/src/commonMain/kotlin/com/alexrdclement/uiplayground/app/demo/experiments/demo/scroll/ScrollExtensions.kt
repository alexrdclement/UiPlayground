package com.alexrdclement.uiplayground.app.demo.experiments.demo.scroll

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.app.demo.experiments.demo.fade.FadeSide
import com.alexrdclement.uiplayground.app.demo.experiments.demo.fade.fade
import com.alexrdclement.uiplayground.app.preview.UiPlaygroundPreview
import com.alexrdclement.uiplayground.components.core.Button
import com.alexrdclement.uiplayground.components.core.Text
import com.alexrdclement.uiplayground.theme.PlaygroundSpacing
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.roundToInt

/**
 * Animate scrolling a partially visible item into the viewport.
 * @param itemKey The key of the item to make visible
 * @param visibleRect The rect within the viewport where the item should be visible.
 *                   Coordinates are relative to the viewport (0,0 = top-left of viewport).
 *                   If null, uses the entire viewport.
 * @param visibilityThreshold The minimum fraction of the item that should be visible (0.0 to 1.0)
 */
suspend fun LazyListState.animateScrollItemVisible(
    itemKey: Any,
    visibleRect: IntRect? = null,
    visibilityThreshold: Float = 1f,
) {
    val scrollPx = calculateItemScrollPx(
        itemKey = itemKey,
        listState = this,
        visibilityThreshold = visibilityThreshold,
        visibleRect = visibleRect,
    ) ?: return
    animateScrollBy(scrollPx)
}

private fun calculateItemScrollPx(
    itemKey: Any,
    listState: LazyListState,
    visibilityThreshold: Float,
    visibleRect: IntRect?,
): Float? {
    val layoutInfo = listState.layoutInfo
    val itemLayoutInfo = layoutInfo.visibleItemsInfo
        .firstOrNull { it.key == itemKey } ?: return null

    val viewportStartOffset = layoutInfo.viewportStartOffset
    val viewportEndOffset = layoutInfo.viewportEndOffset
    val isVertical = layoutInfo.orientation == Orientation.Vertical

    val targetStartOffset = if (visibleRect != null) {
        if (isVertical) {
            viewportStartOffset + visibleRect.top
        } else {
            viewportStartOffset + visibleRect.left
        }
    } else {
        viewportStartOffset
    }

    val targetEndOffset = if (visibleRect != null) {
        if (isVertical) {
            viewportStartOffset + visibleRect.bottom
        } else {
            viewportStartOffset + visibleRect.right
        }
    } else {
        viewportEndOffset
    }

    val itemStart = itemLayoutInfo.offset
    val itemEnd = itemLayoutInfo.offset + itemLayoutInfo.size

    val visibleStart = maxOf(itemStart, targetStartOffset)
    val visibleEnd = minOf(itemEnd, targetEndOffset)
    val visibleSize = maxOf(0, visibleEnd - visibleStart)
    val itemVisibleFraction = visibleSize.toFloat() / itemLayoutInfo.size.toFloat()

    if (itemVisibleFraction >= visibilityThreshold) {
        return null
    }

    return when {
        itemEnd <= targetStartOffset -> {
            (itemStart - targetStartOffset).toFloat()
        }
        itemStart >= targetEndOffset -> {
            (itemEnd - targetEndOffset).toFloat()
        }
        else -> {
            val scrollBackward = itemStart - targetStartOffset
            val scrollForward = itemEnd - targetEndOffset

            val visibilityAfterScrollBackward = minOf(itemLayoutInfo.size, targetEndOffset - itemStart).toFloat() / itemLayoutInfo.size
            val visibilityAfterScrollForward = minOf(itemLayoutInfo.size, itemEnd - targetStartOffset).toFloat() / itemLayoutInfo.size

            if (visibilityAfterScrollBackward >= visibilityThreshold) scrollBackward.toFloat()
            else if (visibilityAfterScrollForward >= visibilityThreshold) scrollForward.toFloat()
            else if (visibilityAfterScrollBackward > visibilityAfterScrollForward) scrollBackward.toFloat()
            else scrollForward.toFloat()
        }
    }
}

@Preview
@Composable
fun AnimateScrollItemVisibleVerticalPreview() {
    UiPlaygroundPreview {
        val lazyListState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()
        val items = (0 until 10).toList()

        val itemSize = 100.dp
        val itemVisibilityScrollThreshold = 0.5f

        val fadeLength = 20.dp
        val fadeLengthPx = with(LocalDensity.current) { fadeLength.toPx() }

        LazyColumn(
            state = lazyListState,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(PlaygroundSpacing.xs),
            contentPadding = PaddingValues(vertical = fadeLength),
            modifier = Modifier
                .size(width = itemSize * 2, height = itemSize * 3 + itemSize / 2)
                .fade(sides = FadeSide.Top + FadeSide.Bottom, length = fadeLength),
        ) {
            items(
                items = items,
                key = { it },
            ) { index ->
                Button(
                    onClick = {
                        coroutineScope.launch {
                            lazyListState.animateScrollItemVisible(
                                itemKey = index,
                                visibleRect = with(lazyListState.layoutInfo.viewportSize) {
                                    IntRect(
                                        left = 0,
                                        top = fadeLengthPx.roundToInt(),
                                        right = width,
                                        bottom = height - fadeLengthPx.roundToInt(),
                                    )
                                },
                                visibilityThreshold = itemVisibilityScrollThreshold,
                            )
                        }
                    },
                    modifier = Modifier.size(itemSize),
                ) {
                    Text(text = index.toString())
                }
            }
        }
    }
}

@Preview
@Composable
fun AnimateScrollItemVisibleHorizontalPreview() {
    UiPlaygroundPreview {
        val lazyListState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()
        val items = (0 until 10).toList()

        val itemSize = 100.dp
        val itemVisibilityScrollThreshold = 0.5f

        val fadeLength = 20.dp
        val fadeLengthPx = with(LocalDensity.current) { fadeLength.toPx() }

        LazyRow(
            state = lazyListState,
            horizontalArrangement = Arrangement.spacedBy(PlaygroundSpacing.xs),
            verticalAlignment = Alignment.CenterVertically,
            contentPadding = PaddingValues(horizontal = fadeLength),
            modifier = Modifier
                .size(width = itemSize * 3 + itemSize / 2, height = itemSize * 2)
                .fade(sides = FadeSide.Left + FadeSide.Right, length = fadeLength)
        ) {
            items(
                items = items,
                key = { it },
            ) { index ->
                Button(
                    onClick = {
                        coroutineScope.launch {
                            lazyListState.animateScrollItemVisible(
                                itemKey = index,
                                visibleRect = with(lazyListState.layoutInfo.viewportSize) {
                                    IntRect(
                                        left = fadeLengthPx.roundToInt(),
                                        top = 0,
                                        right = width - fadeLengthPx.roundToInt(),
                                        bottom = height,
                                    )
                                },
                                visibilityThreshold = itemVisibilityScrollThreshold,
                            )
                        }
                    },
                    modifier = Modifier.size(itemSize),
                ) {
                    Text(text = index.toString())
                }
            }
        }
    }
}
