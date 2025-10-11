package com.alexrdclement.uiplayground.components.core

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.components.LocalContentColor
import com.alexrdclement.uiplayground.components.preview.BoolPreviewParameterProvider
import com.alexrdclement.uiplayground.theme.ColorToken
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import com.alexrdclement.uiplayground.theme.ShapeToken
import com.alexrdclement.uiplayground.theme.styles.ButtonStyleToken
import com.alexrdclement.uiplayground.theme.styles.stroke
import com.alexrdclement.uiplayground.theme.styles.toStyle
import com.alexrdclement.uiplayground.theme.toColor
import com.alexrdclement.uiplayground.theme.toComposeShape
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

@Composable
fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: ButtonStyleToken = ButtonStyleToken.Primary,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPaddingDefault,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit
) {
    val style = style.toStyle()
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        contentColor = style.contentColor,
        contentPadding = contentPadding,
        containerColor = style.containerColor,
        shape = style.shape,
        borderStroke = style.border?.stroke,
        interactionSource = interactionSource,
        content = content,
    )
}

@Composable
internal fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentColor: ColorToken = ColorToken.Primary,
    contentPadding: PaddingValues = ButtonDefaults.ContentPaddingDefault,
    containerColor: ColorToken = ColorToken.Surface,
    shape: ShapeToken = ShapeToken.Primary,
    borderStroke: BorderStroke? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit
) {
    val containerColor = containerColor.toColor().copy(
        alpha = if (enabled) 1f else PlaygroundTheme.colorScheme.disabledContainerAlpha,
    )
    val contentColor = contentColor.toColor().copy(
        alpha = if (enabled) 1f else PlaygroundTheme.colorScheme.disabledContentAlpha,
    )
    Surface(
        onClick = onClick,
        modifier = modifier.semantics { role = Role.Button },
        enabled = enabled,
        shape = shape.toComposeShape(),
        color = containerColor,
        contentColor = contentColor,
        border = borderStroke,
        interactionSource = interactionSource
    ) {
        val mergedTextStyle = LocalTextStyle.current.merge(PlaygroundTheme.typography.labelLarge)
        CompositionLocalProvider(
            LocalContentColor provides contentColor,
            LocalTextStyle provides mergedTextStyle,
        ) {
            Row(
                Modifier
                    .defaultMinSize(
                        minWidth = ButtonDefaults.MinWidth,
                        minHeight = ButtonDefaults.MinHeight
                    )
                    .padding(contentPadding),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                content = content
            )
        }
    }
}

object ButtonDefaults {
    val MinWidth = 58.dp
    val MinHeight = 40.dp

    val ContentPaddingDefault: PaddingValues
        @Composable
        get() = PaddingValues(
            horizontal = PlaygroundTheme.spacing.large,
            vertical = PlaygroundTheme.spacing.small
        )

    val ContentPaddingCompact: PaddingValues
        @Composable
        get() = PaddingValues(
            horizontal = PlaygroundTheme.spacing.medium,
            vertical = 0.dp
        )
}

@Preview
@Composable
private fun PreviewPrimaryStyle(
    @PreviewParameter(BoolPreviewParameterProvider::class) isDarkMode: Boolean,
    @PreviewParameter(BoolPreviewParameterProvider::class) enabled: Boolean,
) {
    PlaygroundTheme(isDarkMode = isDarkMode) {
        Button(
            style = ButtonStyleToken.Primary,
            enabled = enabled,
            onClick = {},
        ) {
            Text("Button")
        }
    }
}

@Preview
@Composable
private fun PreviewSecondaryStyle(
    @PreviewParameter(BoolPreviewParameterProvider::class) isDarkMode: Boolean,
    @PreviewParameter(BoolPreviewParameterProvider::class) enabled: Boolean,
) {
    PlaygroundTheme(isDarkMode = isDarkMode) {
        Button(
            style = ButtonStyleToken.Secondary,
            enabled = enabled,
            onClick = {},
        ) {
            Text("Button")
        }
    }
}

@Preview
@Composable
private fun PreviewTertiaryStyle(
    @PreviewParameter(BoolPreviewParameterProvider::class) isDarkMode: Boolean,
    @PreviewParameter(BoolPreviewParameterProvider::class) enabled: Boolean,
) {
    PlaygroundTheme(isDarkMode = isDarkMode) {
        Surface {
            Button(
                style = ButtonStyleToken.Tertiary,
                enabled = enabled,
                onClick = {},
            ) {
                Text("Button")
            }
        }
    }
}

@Preview
@Composable
private fun ButtonPreview() {
    val isDarkMode = true
    val enabled = true
    val interactionSource = MutableInteractionSource().apply {
        this.tryEmit(PressInteraction.Press(Offset.Zero))
    }

    PlaygroundTheme(isDarkMode = isDarkMode) {
        Surface {
            Button(
                enabled = enabled,
                interactionSource = interactionSource,
                onClick = {},
            ) {
                Text("Button")
            }
        }
    }
}
