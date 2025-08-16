package com.alexrdclement.uiplayground.app.demo.experiments.demo.scroll

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.app.demo.control.Control
import com.alexrdclement.uiplayground.app.demo.control.Controls
import com.alexrdclement.uiplayground.app.demo.experiments.demo.fade.FadeSide
import com.alexrdclement.uiplayground.app.demo.experiments.demo.fade.fade
import com.alexrdclement.uiplayground.app.demo.util.onlyDigits
import com.alexrdclement.uiplayground.app.demo.util.onlyDigitsAndDecimalPoint
import com.alexrdclement.uiplayground.app.preview.UiPlaygroundPreview
import com.alexrdclement.uiplayground.components.Button
import com.alexrdclement.uiplayground.components.HorizontalDivider
import com.alexrdclement.uiplayground.components.Text
import com.alexrdclement.uiplayground.theme.PlaygroundSpacing
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.roundToInt

private const val initialItemCount = 8
private val itemSize = DpSize(80.dp, 80.dp)
private val fadeLengthInitial = 40.dp
private const val itemVisibilityScrollThresholdInitial = 0.7f

@Composable
fun AnimateScrollItemVisibleDemo(
    modifier: Modifier = Modifier,
) {
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val fadeLengthTextFieldState = rememberTextFieldState(
        initialText = fadeLengthInitial.value.roundToInt().toString(),
    )
    val fadeLengthControl = Control.TextField(
        name = "Fade length",
        textFieldState = fadeLengthTextFieldState,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
        ),
        inputTransformation = InputTransformation.onlyDigits(),
    )

    val fadeLength =
        fadeLengthTextFieldState.text.toString().toIntOrNull()?.dp ?: fadeLengthInitial
    val bottomFadeHeightPx = with(LocalDensity.current) { fadeLength.toPx().roundToInt() }

    var showFadeBorders by remember { mutableStateOf(false) }
    val bottomFadeBorderControl = Control.Toggle(
        name = "Fade border",
        value = showFadeBorders,
        onValueChange = { showFadeBorders = it },
    )

    val itemVisibilityThresholdControlTextField = rememberTextFieldState(
        initialText = itemVisibilityScrollThresholdInitial.toString(),
    )
    val itemVisibilityScrollThreshold =
        itemVisibilityThresholdControlTextField.text.toString().toFloatOrNull()
            ?: itemVisibilityScrollThresholdInitial
    val itemVisibilityScrollThresholdControl = Control.TextField(
        name = "Item visibility threshold",
        textFieldState = itemVisibilityThresholdControlTextField,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Decimal,
        ),
        inputTransformation = InputTransformation.onlyDigitsAndDecimalPoint(),
    )

    var items by remember { mutableStateOf((0 until initialItemCount).toList()) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(PlaygroundSpacing.xs),
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Button(
                    onClick = {
                        items = items.plus(items.size).toList()
                    },
                ) {
                    Text("Add")
                }
                Button(
                    onClick = {
                        items = items.minus(items.size - 1).toList()
                    },
                ) {
                    Text("Remove")
                }
            }
            LazyColumn(
                state = lazyListState,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(PlaygroundSpacing.xs),
                contentPadding = PaddingValues(vertical = fadeLength),
                modifier = Modifier.fade(
                    sides = FadeSide.Top + FadeSide.Bottom,
                    length = fadeLength,
                    borderColor = Color.Red.takeIf { showFadeBorders },
                ),
            ) {
                items(
                    items = items,
                    key = { it },
                ) { index ->
                    Button(
                        modifier = Modifier.size(itemSize),
                        onClick = {
                            coroutineScope.launch {
                                lazyListState.animateScrollItemVisible(
                                    itemKey = index,
                                    visibilityThreshold = itemVisibilityScrollThreshold,
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
        HorizontalDivider(modifier = Modifier.fillMaxWidth())
        Controls(
            controls = persistentListOf(
                bottomFadeBorderControl,
                fadeLengthControl,
                itemVisibilityScrollThresholdControl,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(PlaygroundTheme.spacing.medium)
                .navigationBarsPadding(),
        )
    }
}

@Preview
@Composable
private fun ScrollItemVisibleDemoPreview() {
    UiPlaygroundPreview {
        AnimateScrollItemVisibleDemo()
    }
}
