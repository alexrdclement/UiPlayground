package com.alexrdclement.uiplayground.components.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Shape
import com.alexrdclement.uiplayground.theme.ColorToken
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import com.alexrdclement.uiplayground.theme.ShapeToken
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class ChevronDirection {
    Up,
    Down,
    Left,
    Right,
}

@Composable
fun ChevronButton(
    direction: ChevronDirection,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(PlaygroundTheme.spacing.medium),
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        contentColor = ColorToken.Primary,
        containerColor = ColorToken.Surface,
        contentPadding = contentPadding,
        modifier = modifier
    ) {
        ChevronIcon(
            direction = direction,
        )
    }
}

@Composable
fun ChevronIcon(
    direction: ChevronDirection,
    modifier: Modifier = Modifier,
) {
    val rotation = when (direction) {
        ChevronDirection.Left -> 0f
        ChevronDirection.Up -> 90f
        ChevronDirection.Down -> 270f
        ChevronDirection.Right -> 180f
    }
    Box(
        modifier = modifier
            .fillMaxHeight()
            .aspectRatio(1f, matchHeightConstraintsFirst = false)
            .rotate(rotation)
            .clip(ChevronIconShape)
            .background(PlaygroundTheme.colorScheme.primary)
    )
}

private const val ChevronIconWidthProportion = 0.8f
private val ChevronIconShape: Shape = GenericShape { size, _ ->
    val offsetEndX = size.width * ChevronIconWidthProportion
    moveTo(offsetEndX, 0f)
    lineTo(0f, size.height / 2f)
    lineTo(offsetEndX, size.height)
    lineTo(offsetEndX, 0f)
}

@Preview
@Composable
private fun ChevronButtonPreview() {
    PlaygroundTheme {
        Surface {
            ChevronButton(
                direction = ChevronDirection.Right,
                onClick = {},
            )
        }
    }
}

@Preview
@Composable
private fun ChevronIconPreview() {
    PlaygroundTheme {
        Surface {
            ChevronIcon(
                direction = ChevronDirection.Right,
            )
        }
    }
}
