package com.alexrdclement.uiplayground.app.demo.experiments.demo.keyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.alexrdclement.palette.components.core.Checkbox
import com.alexrdclement.palette.components.core.Text
import com.alexrdclement.palette.components.core.TextField
import com.alexrdclement.palette.theme.PaletteTheme
import com.alexrdclement.uiplayground.app.preview.UiPlaygroundPreview
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun KeyboardDemo(
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current

    val imeInsets = WindowInsets.ime
    val imeAnimationSourceInsets = WindowInsets.imeAnimationSource
    val imeAnimationTargetInsets = WindowInsets.imeAnimationTarget
    val safeDrawing = WindowInsets.safeDrawing

    val imeBottomPx = imeInsets.getBottom(density)
    val imeAnimationSourcePx = imeAnimationSourceInsets.getBottom(density)
    val imeAnimationTargetPx = imeAnimationTargetInsets.getBottom(density)
    val safeDrawingBottomPx = safeDrawing.getBottom(density)

    val imeBottomDp = with(density) { imeBottomPx.toDp() }
    val imeAnimationSourceDp = with(density) { imeAnimationSourcePx.toDp() }
    val imeAnimationTargetDp = with(density) { imeAnimationTargetPx.toDp() }
    val safeDrawingBottomDp = with(density) { safeDrawingBottomPx.toDp() }

    val scrollState = rememberScrollState()

    val keyboardBoxHeight = 40.dp
    val keyboardBoxHeightPx = with(density) { keyboardBoxHeight.toPx().toInt() }

    var isWhiteBoxEnabled by remember { mutableStateOf(true) }
    var isCyanBoxEnabled by remember { mutableStateOf(true) }
    var isYellowBoxEnabled by remember { mutableStateOf(true) }
    var isMagentaBoxEnabled by remember { mutableStateOf(true) }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(PaletteTheme.spacing.large),
            modifier = Modifier
                .fillMaxSize()
                .padding(PaletteTheme.spacing.medium)
                .imeNestedScroll()
                .verticalScroll(scrollState)
        ) {
            TextField(
                state = rememberTextFieldState("Top text field"),
                modifier = Modifier.fillMaxWidth()
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(PaletteTheme.spacing.small),
            ) {
                Text(
                    text = "IME Bottom: $imeBottomDp (${imeBottomPx}px)",
                    style = PaletteTheme.typography.bodyMedium,
                )
                Text(
                    text = "IME Animation Source: $imeAnimationSourceDp (${imeAnimationSourcePx}px)",
                    style = PaletteTheme.typography.bodyMedium,
                )
                Text(
                    text = "IME Animation Target: $imeAnimationTargetDp (${imeAnimationTargetPx}px)",
                    style = PaletteTheme.typography.bodyMedium,
                )
                Text(
                    text = "Safe Drawing Bottom: $safeDrawingBottomDp (${safeDrawingBottomPx}px)",
                    style = PaletteTheme.typography.bodyMedium,
                )
                Text(
                    text = "Keyboard Visible: ${imeBottomPx > 0}",
                    style = PaletteTheme.typography.bodyMedium,
                )
                Text(
                    text = "Animation In Progress: ${imeAnimationSourcePx != imeAnimationTargetPx}",
                    style = PaletteTheme.typography.bodyMedium,
                )
                BoxControl(
                    title = "White Box (below bottom text field)",
                    isEnabled = isWhiteBoxEnabled,
                    onClick = { isWhiteBoxEnabled = !isWhiteBoxEnabled },
                )
                BoxControl(
                    title = "Cyan Box (fixed to keyboard top)",
                    isEnabled = isCyanBoxEnabled,
                    onClick = { isCyanBoxEnabled = !isCyanBoxEnabled },
                )
                BoxControl(
                    title = "Yellow Box (appears when keyboard opens)",
                    isEnabled = isYellowBoxEnabled,
                    onClick = { isYellowBoxEnabled = !isYellowBoxEnabled },
                )
                BoxControl(
                    title = "Magenta Box (progressive slide animation)",
                    isEnabled = isMagentaBoxEnabled,
                    onClick = { isMagentaBoxEnabled = !isMagentaBoxEnabled },
                )
            }

            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier.weight(1f)
            ) {
                TextField(
                    state = rememberTextFieldState("Bottom text field"),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // White box below the bottom text field
            if (isWhiteBoxEnabled) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .widthIn(max = 600.dp)
                        .height(keyboardBoxHeight)
                        .background(Color.White)
                )
            }
        }

        // Cyan box follows the keyboard (fixed to screen bottom + keyboard offset)
        if (isCyanBoxEnabled) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset { IntOffset(x = 0, y = -imeBottomPx) }
                    .fillMaxWidth()
                    .widthIn(max = 600.dp)
                    .height(keyboardBoxHeight)
                    .background(Color.Cyan)
            )
        }

        // Yellow box appears when the keyboard opens
        if (isYellowBoxEnabled) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset {
                        IntOffset(x = 0, y = if (imeBottomPx > 0) -imeBottomPx else keyboardBoxHeightPx)
                    }
                    .fillMaxWidth()
                    .widthIn(max = 600.dp)
                    .height(keyboardBoxHeight)
                    .background(Color.Yellow)
            )
        }

        // Magenta box with progressive slide animation (on top of cyan)
        if (isMagentaBoxEnabled) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset {
                        // Calculate visibility progress (0 = hidden, 1 = visible at keyboard)
                        val progress = if (imeAnimationSourcePx != imeAnimationTargetPx) {
                            // Calculate how far through the animation we are
                            val animationRange = imeAnimationTargetPx - imeAnimationSourcePx
                            val animProgress = ((imeBottomPx - imeAnimationSourcePx).toFloat() / animationRange).coerceIn(0f, 1f)

                            // If closing (target < source), invert progress so box stays visible at start
                            if (imeAnimationTargetPx < imeAnimationSourcePx) {
                                1f - animProgress
                            } else {
                                animProgress
                            }
                        } else {
                            // No animation, just check if keyboard is visible
                            if (imeBottomPx > 0) 1f else 0f
                        }

                        // Start below screen, progressively slide up to keyboard top
                        val yOffset = -imeBottomPx + (keyboardBoxHeightPx * (1 - progress)).toInt()
                        IntOffset(x = 0, y = yOffset)
                    }
                    .fillMaxWidth()
                    .widthIn(max = 600.dp)
                    .height(keyboardBoxHeight)
                    .background(Color.Magenta)
            )
        }
    }
}

@Composable
private fun BoxControl(
    title: String,
    isEnabled: Boolean,
    onClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(PaletteTheme.spacing.small),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Checkbox(
            isChecked = isEnabled,
            onCheckedChange = { onClick() },
        )
        Text(
            text = title,
            style = PaletteTheme.typography.bodyMedium,
        )
    }
}

@Preview
@Composable
fun KeyboardDemoPreview() {
    UiPlaygroundPreview {
        KeyboardDemo()
    }
}
