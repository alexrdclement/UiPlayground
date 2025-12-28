package com.alexrdclement.uiplayground.components.core

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import com.alexrdclement.uiplayground.components.LocalContentColor
import com.alexrdclement.uiplayground.components.contentColorFor
import com.alexrdclement.uiplayground.components.preview.BoolPreviewParameterProvider
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import com.alexrdclement.uiplayground.theme.Shape
import com.alexrdclement.uiplayground.theme.toComposeShape
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

@Composable
fun Surface(
    modifier: Modifier = Modifier,
    shape: Shape = PlaygroundTheme.shapeScheme.surface,
    color: Color = PlaygroundTheme.colorScheme.surface,
    contentColor: Color = contentColorFor(color),
    border: BorderStroke? = null,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalContentColor provides contentColor,
    ) {
        Box(
            modifier = modifier
                .shapeLayout(shape)
                .surface(
                    composeShape = shape.toComposeShape(),
                    backgroundColor = color,
                    border = border,
                )
                .semantics(mergeDescendants = false) {
                    isTraversalGroup = true
                }
                .pointerInput(Unit) {},
            propagateMinConstraints = true
        ) {
            content()
        }
    }
}

@Composable
fun Surface(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    onLongClickLabel: String? = null,
    onLongClick: (() -> Unit)? = null,
    onDoubleClick: (() -> Unit)? = null,
    hapticFeedbackEnabled: Boolean = true,
    enabled: Boolean = true,
    shape: Shape = PlaygroundTheme.shapeScheme.surface,
    color: Color = PlaygroundTheme.colorScheme.surface,
    contentColor: Color = contentColorFor(color),
    border: BorderStroke? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalContentColor provides contentColor
    ) {
        Box(
            propagateMinConstraints = true,
            modifier = modifier
                .shapeLayout(shape)
                .shapeClickable(
                    shape = shape,
                    interactionSource = interactionSource,
                    indication = PlaygroundTheme.indication,
                    enabled = enabled,
                    onClick = onClick,
                    onLongClickLabel = onLongClickLabel,
                    onLongClick = onLongClick,
                    onDoubleClick = onDoubleClick,
                    hapticFeedbackEnabled = hapticFeedbackEnabled,
                )
                .surface(
                    composeShape = shape.toComposeShape(),
                    backgroundColor = color,
                    border = border,
                )
        ) {
            content()
        }
    }
}

@Stable
private fun Modifier.surface(
    composeShape: androidx.compose.ui.graphics.Shape,
    backgroundColor: Color,
    border: BorderStroke?,
) = this
    .graphicsLayer(shape = composeShape, clip = true)
    .then(if (border != null) Modifier.border(border, composeShape) else Modifier)
    .background(color = backgroundColor, shape = composeShape)

@Preview
@Composable
private fun Preview(
    @PreviewParameter(BoolPreviewParameterProvider::class) isDarkTheme: Boolean,
) {
    PlaygroundTheme(isDarkMode = isDarkTheme) {
        Surface {
            Text("Hello world")
        }
    }
}

@Preview
@Composable
private fun PreviewClickable(
    @PreviewParameter(BoolPreviewParameterProvider::class) isDarkTheme: Boolean,
    @PreviewParameter(BoolPreviewParameterProvider::class) enabled: Boolean,
) {
    PlaygroundTheme(isDarkMode = isDarkTheme) {
        Surface(
            onClick = {},
            enabled = enabled,
        ) {
            Text("Hello world")
        }
    }
}
