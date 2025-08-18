package com.alexrdclement.uiplayground.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import com.alexrdclement.uiplayground.components.preview.BoolPreviewParameterProvider
import com.alexrdclement.uiplayground.theme.LocalPlaygroundIndication
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

@Composable
fun Surface(
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
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
                .surface(
                    shape = shape,
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
    enabled: Boolean = true,
    shape: Shape = RectangleShape,
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
            modifier = modifier
                .surface(
                    shape = shape,
                    backgroundColor = color,
                    border = border,
                )
                .clickable(
                    interactionSource = interactionSource,
                    indication = LocalPlaygroundIndication.current,
                    enabled = enabled,
                    onClick = onClick
                ),
            propagateMinConstraints = true
        ) {
            content()
        }
    }
}

@Stable
private fun Modifier.surface(
    shape: Shape,
    backgroundColor: Color,
    border: BorderStroke?,
) = this
    .graphicsLayer(shape = shape, clip = false)
    .then(if (border != null) Modifier.border(border, shape) else Modifier)
    .background(color = backgroundColor, shape = shape)
    .clip(shape)

@Preview
@Composable
private fun Preview(
    @PreviewParameter(BoolPreviewParameterProvider::class) isDarkTheme: Boolean,
) {
    PlaygroundTheme(darkTheme = isDarkTheme) {
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
    PlaygroundTheme(darkTheme = isDarkTheme) {
        Surface(
            onClick = {},
            enabled = enabled,
        ) {
            Text("Hello world")
        }
    }
}
