package com.alexrdclement.uiplayground.components.menu

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.alexrdclement.uiplayground.components.LocalContentColor
import com.alexrdclement.uiplayground.components.core.Surface
import com.alexrdclement.uiplayground.components.menu.MenuDefaults.DropdownMenuItemDefaultMaxWidth
import com.alexrdclement.uiplayground.components.menu.MenuDefaults.DropdownMenuItemDefaultMinHeight
import com.alexrdclement.uiplayground.components.menu.MenuDefaults.DropdownMenuItemDefaultMinWidth
import com.alexrdclement.uiplayground.components.menu.MenuDefaults.DropdownMenuVerticalPadding
import com.alexrdclement.uiplayground.components.menu.MenuDefaults.InTransitionDuration
import com.alexrdclement.uiplayground.components.menu.MenuDefaults.MenuBorderWidth
import com.alexrdclement.uiplayground.components.menu.MenuDefaults.OutTransitionDuration
import com.alexrdclement.uiplayground.theme.ColorScheme
import com.alexrdclement.uiplayground.theme.LocalPlaygroundIndication
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import com.alexrdclement.uiplayground.theme.ShapeToken
import com.alexrdclement.uiplayground.theme.toComposeShape
import com.alexrdclement.uiplayground.theme.toShape
import kotlin.math.max
import kotlin.math.min

// Adapted from Material3's DropdownMenu.

@Composable
fun DropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    scrollState: ScrollState = rememberScrollState(),
    properties: PopupProperties = PopupProperties(focusable = true),
    content: @Composable ColumnScope.() -> Unit
) {
    val expandedState = remember { MutableTransitionState(false) }
    expandedState.targetState = expanded

    if (expandedState.currentState || expandedState.targetState) {
        val transformOriginState = remember { mutableStateOf(TransformOrigin.Center) }
        val density = LocalDensity.current
        val popupPositionProvider = remember(offset, density) {
            DropdownMenuPositionProvider(
                offset,
                density
            ) { parentBounds, menuBounds ->
                transformOriginState.value = calculateTransformOrigin(parentBounds, menuBounds)
            }
        }

        Popup(
            onDismissRequest = onDismissRequest,
            popupPositionProvider = popupPositionProvider,
            properties = properties
        ) {
            DropdownMenuContent(
                expandedState = expandedState,
                transformOriginState = transformOriginState,
                scrollState = scrollState,
                modifier = modifier,
                content = content
            )
        }
    }
}

@Composable
internal fun DropdownMenuContent(
    expandedState: MutableTransitionState<Boolean>,
    transformOriginState: MutableState<TransformOrigin>,
    scrollState: ScrollState,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    // Menu open/close animation.
    val transition = rememberTransition(expandedState, "DropDownMenu")

    val scale by transition.animateFloat(
        transitionSpec = {
            if (false isTransitioningTo true) {
                // Dismissed to expanded
                tween(
                    durationMillis = InTransitionDuration,
                    easing = LinearOutSlowInEasing
                )
            } else {
                // Expanded to dismissed.
                tween(
                    durationMillis = 1,
                    delayMillis = OutTransitionDuration - 1
                )
            }
        },
        label = "DropdownMenuScale"
    ) { expanded ->
        if (expanded) 1f else 0.8f
    }

    val alpha by transition.animateFloat(
        transitionSpec = {
            if (false isTransitioningTo true) {
                // Dismissed to expanded
                tween(durationMillis = 30)
            } else {
                // Expanded to dismissed.
                tween(durationMillis = OutTransitionDuration)
            }
        },
        label = "DropdownMenuAlpha"
    ) { expanded ->
        if (expanded) 1f else 0f
    }

    Surface(
        border = BorderStroke(MenuBorderWidth, PlaygroundTheme.colorScheme.outline),
        modifier = Modifier.graphicsLayer {
            scaleX = scale
            scaleY = scale
            this.alpha = alpha
            transformOrigin = transformOriginState.value
        }
    ) {
        Column(
            modifier = modifier
                .padding(vertical = DropdownMenuVerticalPadding)
                .width(IntrinsicSize.Max)
                .verticalScroll(scrollState),
            content = content
        )
    }
}

@Composable
fun DropdownMenuItem(
    text: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: MenuItemColors = MenuDefaults.itemColors(),
    contentPadding: PaddingValues = MenuDefaults.DropdownMenuItemContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    Row(
        modifier = modifier
            .clickable(
                enabled = enabled,
                onClick = onClick,
                interactionSource = interactionSource,
                indication = LocalPlaygroundIndication.current,
            )
            .fillMaxWidth()
            // Preferred min and max width used during the intrinsic measurement.
            .sizeIn(
                minWidth = DropdownMenuItemDefaultMinWidth,
                maxWidth = DropdownMenuItemDefaultMaxWidth,
                minHeight = DropdownMenuItemDefaultMinHeight,
            )
            .padding(contentPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CompositionLocalProvider(
            LocalContentColor provides colors.textColor(enabled).value,
        ) {
            Box(modifier = Modifier.weight(1f)) {
                text()
            }
        }
    }
}

internal fun calculateTransformOrigin(
    anchorBounds: IntRect,
    menuBounds: IntRect
): TransformOrigin {
    val pivotX = when {
        menuBounds.left >= anchorBounds.right -> 0f
        menuBounds.right <= anchorBounds.left -> 1f
        menuBounds.width == 0 -> 0f
        else -> {
            val intersectionCenter =
                (max(anchorBounds.left, menuBounds.left) +
                        min(anchorBounds.right, menuBounds.right)) / 2
            (intersectionCenter - menuBounds.left).toFloat() / menuBounds.width
        }
    }
    val pivotY = when {
        menuBounds.top >= anchorBounds.bottom -> 0f
        menuBounds.bottom <= anchorBounds.top -> 1f
        menuBounds.height == 0 -> 0f
        else -> {
            val intersectionCenter =
                (max(anchorBounds.top, menuBounds.top) +
                        min(anchorBounds.bottom, menuBounds.bottom)) / 2
            (intersectionCenter - menuBounds.top).toFloat() / menuBounds.height
        }
    }
    return TransformOrigin(pivotX, pivotY)
}

@Immutable
class MenuItemColors(
    val textColor: Color,
    val disabledTextColor: Color,
) {
    fun copy(
        textColor: Color = this.textColor,
        disabledTextColor: Color = this.disabledTextColor,
    ) = MenuItemColors(
        textColor.takeOrElse { this.textColor },
        disabledTextColor.takeOrElse { this.disabledTextColor },
    )

    @Composable
    internal fun textColor(enabled: Boolean): State<Color> {
        return rememberUpdatedState(if (enabled) textColor else disabledTextColor)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is MenuItemColors) return false

        if (textColor != other.textColor) return false
        if (disabledTextColor != other.disabledTextColor) return false

        return true
    }


    override fun hashCode(): Int {
        var result = textColor.hashCode()
        result = 31 * result + disabledTextColor.hashCode()
        return result
    }

}

object MenuDefaults {

    @Composable
    fun itemColors() = PlaygroundTheme.colorScheme.defaultMenuItemColors

    @Composable
    fun itemColors(
        textColor: Color = Color.Unspecified,
        disabledTextColor: Color = Color.Unspecified,
    ): MenuItemColors = PlaygroundTheme.colorScheme.defaultMenuItemColors.copy(
        textColor = textColor,
        disabledTextColor = disabledTextColor,
    )

    internal val ColorScheme.defaultMenuItemColors: MenuItemColors
        @Composable
        get() = with(PlaygroundTheme.colorScheme) {
            remember(this) {
                MenuItemColors(
                    textColor = primary,
                    disabledTextColor = onSurface.copy(alpha = 0.38f)
                )
            }
        }

    // Size defaults.
    internal val MenuVerticalMargin = 48.dp
    internal val MenuBorderWidth = 1.dp
    private val DropdownMenuItemHorizontalPadding = 12.dp
    internal val DropdownMenuVerticalPadding = 8.dp
    internal val DropdownMenuItemDefaultMinWidth = 112.dp
    internal val DropdownMenuItemDefaultMaxWidth = 280.dp

    // Menu open/close animation.
    internal const val InTransitionDuration = 120
    internal const val OutTransitionDuration = 75

    val DropdownMenuItemContentPadding = PaddingValues(
        horizontal = DropdownMenuItemHorizontalPadding,
        vertical = 0.dp
    )
    val DropdownMenuItemDefaultMinHeight = 48.dp

    val ContainerShape = ShapeToken.Primary
}
