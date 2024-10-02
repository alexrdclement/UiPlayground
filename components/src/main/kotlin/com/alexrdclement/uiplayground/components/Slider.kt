package com.alexrdclement.uiplayground.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.MutatorMutex
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.DragScope
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.setProgress
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import kotlinx.coroutines.coroutineScope
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

// Adapted from Material3's Slider. Does not support steps, ranges, or RTL layout.

@Composable
fun Slider(
    value: Float,
    onValueChange: ((Float) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    onValueChangeFinished: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val onValueChangeFinishedState = rememberUpdatedState(onValueChangeFinished)
    val state = remember(valueRange) {
        SliderState(
            value = value,
            onValueChangeFinished = { onValueChangeFinishedState.value?.invoke() },
            valueRange = valueRange,
        )
    }

    state.onValueChange = onValueChange
    state.value = value

    Slider(
        state = state,
        modifier = modifier,
        enabled = enabled,
        interactionSource = interactionSource,
    )
}

@Composable
fun Slider(
    state: SliderState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    Layout(
        content = {
            SliderDefaults.Track()
            SliderDefaults.Thumb()
        },
        modifier = modifier
            .requiredSizeIn(
                minWidth = SliderDefaults.ThumbSize,
                minHeight = SliderDefaults.ThumbSize,
            )
            .sliderSemantics(state, enabled)
            .focusable(enabled, interactionSource)
            .then(
                if (enabled) {
                    Modifier.pointerInput(state, interactionSource) {
                        detectTapGestures(
                            onPress = { state.onPress(it) },
                            onTap = {
                                state.dispatchRawDelta(0f)
                                state.gestureEndAction()
                            }
                        )
                    }
                } else Modifier
            )
            .draggable(
                orientation = Orientation.Horizontal,
                enabled = enabled,
                interactionSource = interactionSource,
                onDragStopped = { state.gestureEndAction() },
                startDragImmediately = state.isDragging,
                state = state,
            )
    ) { measurables, constraints ->

        val trackPlaceable = measurables[0].measure(constraints)
        val thumbPlaceable = measurables[1].measure(constraints)

        val sliderWidth = thumbPlaceable.width + trackPlaceable.width
        val sliderHeight = max(trackPlaceable.height, thumbPlaceable.height)

        state.updateDimensions(
            thumbPlaceable.width.toFloat(),
            sliderWidth
        )

        val trackOffsetX = thumbPlaceable.width / 2
        val thumbOffsetX = ((trackPlaceable.width) * state.coercedValueAsFraction).roundToInt()
        val trackOffsetY = (sliderHeight - trackPlaceable.height) / 2
        val thumbOffsetY = (sliderHeight - thumbPlaceable.height) / 2

        layout(sliderWidth, sliderHeight) {
            trackPlaceable.placeRelative(
                trackOffsetX,
                trackOffsetY
            )
            thumbPlaceable.placeRelative(
                thumbOffsetX,
                thumbOffsetY
            )
        }
    }
}

private fun Modifier.sliderSemantics(
    state: SliderState,
    enabled: Boolean,
) = semantics {
    if (!enabled) disabled()
    setProgress { targetValue ->
        val resolvedValue = targetValue.coerceIn(
            state.valueRange.start,
            state.valueRange.endInclusive
        )

        if (resolvedValue == state.value) {
            return@setProgress false
        }

        if (state.onValueChange != null) {
            state.onValueChange?.let {
                it(resolvedValue)
            }
        } else {
            state.value = resolvedValue
        }
        state.onValueChangeFinished?.invoke()

        true
    }
}.progressSemantics(
    state.value,
    state.valueRange.start..state.valueRange.endInclusive,
)

object SliderDefaults {
    val TrackHeight = 1.dp
    val TrackBrush: Brush
        @Composable
        get() = with(PlaygroundTheme.colorScheme) {
            remember(this) {
                SolidColor(primary)
            }
        }

    val ThumbSize = 20.dp
    val ThumbBorderStroke: BorderStroke
        @Composable
        get() = with(PlaygroundTheme.colorScheme) {
            remember(this) {
                BorderStroke(
                    width = 1.dp,
                    color = primary,
                )
            }
        }
    val ThumbPointSizeDp = 4.dp
    val ThumbPointSize: Size
        @Composable
        get() = with(LocalDensity.current) {
            Size(ThumbPointSizeDp.toPx(), ThumbPointSizeDp.toPx())
        }
    val ThumbPointBrush: Brush
        @Composable
        get() = with(PlaygroundTheme.colorScheme) {
            remember(this) {
                SolidColor(primary)
            }
        }
    val ThumbBackgroundBrush: Brush
        @Composable
        get() = with(PlaygroundTheme.colorScheme) {
            remember(this) {
                SolidColor(surface)
            }
        }

    @Composable
    fun Track() {
        val brush = TrackBrush
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(TrackHeight)
        ) {
            drawLine(
                brush = brush,
                start = Offset(0f, center.y),
                end = Offset(this.size.width, center.y),
                strokeWidth = TrackHeight.toPx(),
            )
        }
    }

    @Composable
    fun Thumb() {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(ThumbSize)
                .border(ThumbBorderStroke)
        ) {
            val pointBrush = ThumbPointBrush
            val pointSize = ThumbPointSize
            val backgroundBrush = ThumbBackgroundBrush
            Canvas(modifier = Modifier.fillMaxSize()) {
                drawRect(
                    brush = backgroundBrush,
                    topLeft = Offset(center.x, 0f),
                    size = Size(this.size.width / 2, this.size.height)
                )
                drawRect(
                    brush = pointBrush,
                    topLeft = Offset(center.x - pointSize.width / 2, center.y - pointSize.height / 2),
                    size = pointSize,
                )
            }
        }
    }
}

class SliderState(
    value: Float = 0f,
    var onValueChange: ((Float) -> Unit)? = null,
    val onValueChangeFinished: (() -> Unit)? = null,
    val valueRange: ClosedFloatingPointRange<Float> = 0f..1f
) : DraggableState {

    private var valueState by mutableFloatStateOf(value)

    var value: Float
        set(newVal) {
            valueState = newVal.coerceIn(valueRange.start, valueRange.endInclusive)
        }
        get() = valueState

    override suspend fun drag(
        dragPriority: MutatePriority,
        block: suspend DragScope.() -> Unit
    ): Unit = coroutineScope {
        isDragging = true
        scrollMutex.mutateWith(dragScope, dragPriority, block)
        isDragging = false
    }

    override fun dispatchRawDelta(delta: Float) {
        val maxPx = max(totalWidth - thumbWidth / 2, 0f)
        val minPx = min(thumbWidth / 2, maxPx)
        rawOffset = (rawOffset + delta + pressOffset)
        pressOffset = 0f
        val offsetInTrack = rawOffset
        val scaledUserValue = scaleToUserValue(minPx, maxPx, offsetInTrack)
        if (scaledUserValue != this.value) {
            if (onValueChange != null) {
                onValueChange?.let { it(scaledUserValue) }
            } else {
                this.value = scaledUserValue
            }
        }
    }

    private var totalWidth by mutableIntStateOf(0)
    private var thumbWidth by mutableFloatStateOf(0f)

    internal val coercedValueAsFraction
        get() = calcFraction(
            valueRange.start,
            valueRange.endInclusive,
            value.coerceIn(valueRange.start, valueRange.endInclusive)
        )

    internal var isDragging by mutableStateOf(false)
        private set

    internal fun updateDimensions(
        newThumbWidth: Float,
        newTotalWidth: Int
    ) {
        thumbWidth = newThumbWidth
        totalWidth = newTotalWidth
    }

    internal val gestureEndAction = {
        if (!isDragging) {
            // check isDragging in case the change is still in progress (touch -> drag case)
            onValueChangeFinished?.invoke()
        }
    }

    internal fun onPress(pos: Offset) {
        pressOffset = pos.x - rawOffset
    }

    private var rawOffset by mutableFloatStateOf(scaleToOffset(0f, 0f, value))
    private var pressOffset by mutableFloatStateOf(0f)
    private val dragScope: DragScope = object : DragScope {
        override fun dragBy(pixels: Float): Unit = dispatchRawDelta(pixels)
    }

    private val scrollMutex = MutatorMutex()

    private fun scaleToUserValue(minPx: Float, maxPx: Float, offset: Float) =
        scale(minPx, maxPx, offset, valueRange.start, valueRange.endInclusive)

    private fun scaleToOffset(minPx: Float, maxPx: Float, userValue: Float) =
        scale(valueRange.start, valueRange.endInclusive, userValue, minPx, maxPx)
}

// Scale x1 from a1..b1 range to a2..b2 range
private fun scale(a1: Float, b1: Float, x1: Float, a2: Float, b2: Float) =
    lerp(a2, b2, calcFraction(a1, b1, x1))

// Calculate the 0..1 fraction that `pos` value represents between `a` and `b`
private fun calcFraction(a: Float, b: Float, pos: Float) =
    (if (b - a == 0f) 0f else (pos - a) / (b - a)).coerceIn(0f, 1f)

@Preview
@Composable
private fun Preview() {
    PlaygroundTheme {
        Surface {
            val state = remember { SliderState(value = 0.5f) }
            Slider(state = state)
        }
    }
}
