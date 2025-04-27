package com.alexrdclement.uiplayground.components.preview

import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

internal class BoolPreviewParameterProvider : PreviewParameterProvider<Boolean> {
    override val values = sequenceOf(true, false)
}
