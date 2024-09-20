package com.alexrdclement.uiplayground.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.components.preview.BoolPreviewParameterProvider
import com.alexrdclement.uiplayground.theme.PlaygroundTheme

@Composable
fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = CircleShape,
    colors: ButtonColors = ButtonDefaults.defaultButtonColors(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit
) {
    val containerColor = colors.containerColor(enabled)
    val contentColor = colors.contentColor(enabled)
    Surface(
        onClick = onClick,
        modifier = modifier.semantics { role = Role.Button },
        enabled = enabled,
        shape = shape,
        color = containerColor,
        contentColor = contentColor,
        border = border,
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

    private val ButtonHorizontalPadding = 24.dp
    private val ButtonVerticalPadding = 8.dp

    val ContentPadding = PaddingValues(
        start = ButtonHorizontalPadding,
        top = ButtonVerticalPadding,
        end = ButtonHorizontalPadding,
        bottom = ButtonVerticalPadding
    )

    @Composable
    fun defaultButtonColors() = with(PlaygroundTheme.colorScheme) {
        remember(this) {
            ButtonColors(
                containerColor = primary,
                contentColor = onPrimary,
                disabledContainerColor = primary.copy(alpha = 0.12f),
                disabledContentColor = onPrimary.copy(alpha = 0.38f)
            )
        }
    }
}

@Immutable
class ButtonColors(
    val containerColor: Color,
    val contentColor: Color,
    val disabledContainerColor: Color,
    val disabledContentColor: Color,
) {
    @Stable
    internal fun containerColor(enabled: Boolean): Color =
        if (enabled) containerColor else disabledContainerColor

    @Stable
    internal fun contentColor(enabled: Boolean): Color =
        if (enabled) contentColor else disabledContentColor

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is ButtonColors) return false

        if (containerColor != other.containerColor) return false
        if (contentColor != other.contentColor) return false
        if (disabledContainerColor != other.disabledContainerColor) return false
        if (disabledContentColor != other.disabledContentColor) return false

        return true
    }

    override fun hashCode(): Int {
        var result = containerColor.hashCode()
        result = 31 * result + contentColor.hashCode()
        result = 31 * result + disabledContainerColor.hashCode()
        result = 31 * result + disabledContentColor.hashCode()
        return result
    }
}

@PreviewLightDark
@Composable
private fun Preview(
    @PreviewParameter(BoolPreviewParameterProvider::class) enabled: Boolean,
) {
    PlaygroundTheme {
        Button(
            enabled = enabled,
            onClick = {},
        ) {
            Text("Button")
        }
    }
}
