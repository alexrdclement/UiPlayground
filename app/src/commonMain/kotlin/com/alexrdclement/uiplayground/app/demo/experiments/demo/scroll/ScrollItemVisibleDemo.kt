package com.alexrdclement.uiplayground.app.demo.experiments.demo.scroll

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.components.demo.Demo
import com.alexrdclement.uiplayground.components.demo.control.Control
import com.alexrdclement.uiplayground.app.demo.experiments.demo.fade.FadeSide
import com.alexrdclement.uiplayground.app.demo.experiments.demo.fade.fade
import com.alexrdclement.uiplayground.app.preview.UiPlaygroundPreview
import com.alexrdclement.uiplayground.components.core.Button
import com.alexrdclement.uiplayground.components.core.Text
import com.alexrdclement.uiplayground.components.util.mapSaverSafe
import com.alexrdclement.uiplayground.theme.PlaygroundSpacing
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.roundToInt

@Composable
fun AnimateScrollItemVisibleDemo(
    modifier: Modifier = Modifier,
    state: AnimateScrollItemVisibleDemoState = rememberAnimateScrollItemVisibleDemoState(),
    control: AnimateScrollItemVisibleDemoControl = rememberAnimateScrollItemVisibleDemoControl(state),
    itemSize: DpSize = DpSize(80.dp, 80.dp)
) {
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val bottomFadeHeightPx = with(LocalDensity.current) { state.fadeLength.toPx().roundToInt() }

    Demo(
        controls = control.controls,
        modifier = modifier
            .fillMaxSize(),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(PlaygroundSpacing.xs),
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Button(
                    onClick = {
                        state.items = state.items.plus(state.items.size).toList()
                    },
                ) {
                    Text("Add")
                }
                Button(
                    onClick = {
                        state.items = state.items.minus(state.items.size - 1).toList()
                    },
                ) {
                    Text("Remove")
                }
            }
            LazyColumn(
                state = lazyListState,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(PlaygroundSpacing.xs),
                contentPadding = PaddingValues(vertical = state.fadeLength),
                modifier = Modifier.fade(
                    sides = FadeSide.Top + FadeSide.Bottom,
                    length = state.fadeLength,
                    borderColor = Color.Red.takeIf { state.showFadeBorders },
                ),
            ) {
                items(
                    items = state.items,
                    key = { it },
                ) { index ->
                    Button(
                        modifier = Modifier.size(itemSize),
                        onClick = {
                            coroutineScope.launch {
                                lazyListState.animateScrollItemVisible(
                                    itemKey = index,
                                    visibilityThreshold = state.itemVisibilityScrollThreshold,
                                    visibleRect = with(lazyListState.layoutInfo.viewportSize) {
                                        IntRect(
                                            left = 0,
                                            top = bottomFadeHeightPx,
                                            right = width,
                                            bottom = height - bottomFadeHeightPx,
                                        )
                                    },
                                )
                            }
                        },
                    ) {
                        Text(index.toString())
                    }
                }
            }
        }
    }
}


@Composable
fun rememberAnimateScrollItemVisibleDemoState(
    initialFadeLength: Dp = 40.dp,
) = rememberSaveable(
    initialFadeLength,
    saver = AnimateScrollItemVisibleDemoStateSaver,
) {
    AnimateScrollItemVisibleDemoState(
        initialFadeLength = initialFadeLength,
    )
}

@Stable
class AnimateScrollItemVisibleDemoState(
    initialFadeLength: Dp = 40.dp,
    initialItemCount: Int = 8,
    initialItemVisibilityScrollThreshold: Float = 0.7f,
    initialShowBorders: Boolean = false,
) {
    var fadeLength by mutableStateOf(initialFadeLength)
        internal set
    var showFadeBorders by mutableStateOf(initialShowBorders)
        internal set
    var items by mutableStateOf((0 until initialItemCount).toList())
        internal set
    var itemVisibilityScrollThreshold by mutableStateOf(initialItemVisibilityScrollThreshold)
        internal set
}

private const val fadeLengthKey = "fadeLength"
private const val itemCountKey = "itemCount"
private const val itemVisibilityScrollThresholdKey = "itemVisibilityScrollThreshold"
private const val showFadeBordersKey = "showFadeBorders"

val AnimateScrollItemVisibleDemoStateSaver = mapSaverSafe(
    save = { value ->
        mapOf(
            fadeLengthKey to value.fadeLength.value,
            itemCountKey to value.items.size,
            itemVisibilityScrollThresholdKey to value.itemVisibilityScrollThreshold,
            showFadeBordersKey to value.showFadeBorders,
        )
    },
    restore = { map ->
        AnimateScrollItemVisibleDemoState(
            initialFadeLength = (map[fadeLengthKey] as Float).dp,
            initialItemCount = map[itemCountKey] as Int,
            initialItemVisibilityScrollThreshold =
                map[itemVisibilityScrollThresholdKey] as Float,
            initialShowBorders = map[showFadeBordersKey] as Boolean,
        )
    }
)

@Composable
fun rememberAnimateScrollItemVisibleDemoControl(
    state: AnimateScrollItemVisibleDemoState = rememberAnimateScrollItemVisibleDemoState(),
) = remember(state) {
    AnimateScrollItemVisibleDemoControl(state)
}

@Stable
class AnimateScrollItemVisibleDemoControl(
    val state: AnimateScrollItemVisibleDemoState,
) {
    val fadeLengthControl = Control.Slider(
        name = "Fade length",
        value = { state.fadeLength.value },
        onValueChange = { state.fadeLength = it.dp },
        valueRange = { 0f..500f },
    )

    val bottomFadeBorderControl = Control.Toggle(
        name = "Fade border",
        value = { state.showFadeBorders },
        onValueChange = { state.showFadeBorders = it },
    )

    val itemVisibilityScrollThresholdControl = Control.Slider(
        name = "Item visibility threshold",
        value = { state.itemVisibilityScrollThreshold },
        onValueChange = { state.itemVisibilityScrollThreshold = it },
        valueRange = { 0f..1f },
    )

    val controls = persistentListOf(
        fadeLengthControl,
        bottomFadeBorderControl,
        itemVisibilityScrollThresholdControl,
    )
}

@Preview
@Composable
private fun ScrollItemVisibleDemoPreview() {
    UiPlaygroundPreview {
        AnimateScrollItemVisibleDemo()
    }
}
