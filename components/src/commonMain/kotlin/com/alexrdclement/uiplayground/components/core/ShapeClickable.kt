package com.alexrdclement.uiplayground.components.core

import androidx.compose.foundation.Indication
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.onLongClick
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.IntSize
import com.alexrdclement.uiplayground.theme.Shape
import kotlinx.coroutines.launch
import kotlin.math.sqrt

fun Modifier.shapeClickable(
    shape: Shape,
    interactionSource: MutableInteractionSource,
    indication: Indication?,
    enabled: Boolean = true,
    hapticFeedbackEnabled: Boolean = true,
    onClick: () -> Unit,
    onLongClick: (() -> Unit)? = null,
    onDoubleClick: (() -> Unit)? = null,
    onLongClickLabel: String? = null,
): Modifier = composed {
    this
        .then(
            if (onLongClick != null && onLongClickLabel != null) {
                Modifier.semantics {
                    onLongClick(label = onLongClickLabel, action = null)
                    role = Role.Button
                }
            } else if (onLongClick != null || enabled) {
                Modifier.semantics {
                    role = Role.Button
                }
            } else {
                Modifier
            }
        )
        .indication(interactionSource, indication)
        .clippedPointerEvents(
            shape = shape,
            interactionSource = interactionSource,
            enabled = enabled,
        )
        .clippedTapGestures(
            shape = shape,
            interactionSource = interactionSource,
            enabled = enabled,
            hapticFeedbackEnabled = hapticFeedbackEnabled,
            onClick = onClick,
            onLongClick = onLongClick,
            onDoubleClick = onDoubleClick,
        )
}

@Stable
private fun Modifier.clippedPointerEvents(
    shape: Shape,
    interactionSource: MutableInteractionSource,
    enabled: Boolean,
) = composed {
    val scope = rememberCoroutineScope()
    var hoverInteraction by remember { mutableStateOf<HoverInteraction.Enter?>(null) }

    this.pointerInput(shape, enabled) {
        if (!enabled) return@pointerInput

        awaitPointerEventScope {
            while (true) {
                val event = awaitPointerEvent(PointerEventPass.Initial)
                val change = event.changes.firstOrNull() ?: continue
                val position = change.position
                val inShape = isInShape(size, position, shape)

                when (event.type) {
                    PointerEventType.Enter, PointerEventType.Move -> {
                        if (inShape && hoverInteraction == null) {
                            val interaction = HoverInteraction.Enter()
                            hoverInteraction = interaction
                            scope.launch {
                                interactionSource.emit(interaction)
                            }
                        } else if (!inShape && hoverInteraction != null) {
                            val interaction = hoverInteraction
                            hoverInteraction = null
                            scope.launch {
                                if (interaction != null) {
                                    interactionSource.emit(HoverInteraction.Exit(interaction))
                                }
                            }
                        }
                    }
                    PointerEventType.Exit -> {
                        val interaction = hoverInteraction
                        hoverInteraction = null
                        scope.launch {
                            if (interaction != null) {
                                interactionSource.emit(HoverInteraction.Exit(interaction))
                            }
                        }
                    }
                }

                if (!inShape && event.type != PointerEventType.Exit) {
                    change.consume()
                }
            }
        }
    }
}

@Stable
fun Modifier.clippedTapGestures(
    shape: Shape,
    interactionSource: MutableInteractionSource,
    enabled: Boolean,
    hapticFeedbackEnabled: Boolean,
    onClick: () -> Unit,
    onLongClick: (() -> Unit)? = null,
    onDoubleClick: (() -> Unit)? = null,
) = composed {
    val scope = rememberCoroutineScope()
    val haptic = LocalHapticFeedback.current
    var pressInteraction by remember { mutableStateOf<PressInteraction.Press?>(null) }

    this.pointerInput(shape, enabled, onClick, onLongClick, onDoubleClick) {
        if (!enabled) return@pointerInput

        detectTapGestures(
            onPress = { offset ->
                if (isInShape(size, offset, shape)) {
                    val interaction = PressInteraction.Press(offset)
                    pressInteraction = interaction
                    scope.launch {
                        interactionSource.emit(interaction)
                    }

                    val released = tryAwaitRelease()

                    val currentInteraction = pressInteraction
                    pressInteraction = null

                    if (currentInteraction != null) {
                        scope.launch {
                            if (released) {
                                interactionSource.emit(PressInteraction.Release(currentInteraction))
                            } else {
                                interactionSource.emit(PressInteraction.Cancel(currentInteraction))
                            }
                        }
                    }
                }
            },
            onTap = { offset ->
                if (isInShape(size, offset, shape)) {
                    onClick()
                }
            },
            onLongPress = if (onLongClick != null) {
                { offset ->
                    if (isInShape(size, offset, shape)) {
                        if (hapticFeedbackEnabled) {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        }
                        onLongClick()
                    }
                }
            } else null,
            onDoubleTap = if (onDoubleClick != null) {
                { offset ->
                    if (isInShape(size, offset, shape)) {
                        onDoubleClick()
                    }
                }
            } else null
        )
    }
}

private fun isInShape(
    size: IntSize,
    offset: Offset,
    shape: Shape,
): Boolean {
    return when (shape) {
        Shape.Circle -> {
            val centerX = size.width / 2f
            val centerY = size.height / 2f
            val radius = size.width / 2f

            val dx = offset.x - centerX
            val dy = offset.y - centerY
            val distance = sqrt(dx * dx + dy * dy)

            distance <= radius
        }
        else -> true
    }
}
