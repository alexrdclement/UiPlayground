package com.alexrdclement.uiplayground.components.core

import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import com.alexrdclement.uiplayground.theme.Shape


@Stable
fun Modifier.shapeLayout(
    shape: Shape,
) = this.layout { measurable, constraints ->
    val initialPlaceable = measurable.measure(constraints)

    val placeable = if (shape.hasEqualAspectRatio) {
        val max = maxOf(initialPlaceable.width, initialPlaceable.height)
        measurable.measure(
            constraints.copy(
                minWidth = max,
                maxWidth = max,
                minHeight = max,
                maxHeight = max
            )
        )
    } else {
        initialPlaceable
    }

    layout(placeable.width, placeable.height) {
        placeable.placeRelative(0, 0)
    }
}
