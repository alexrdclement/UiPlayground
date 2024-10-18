package com.alexrdclement.uiplayground.components.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

internal class BoolPreviewParameterProvider : PreviewParameterProvider<Boolean> {
    override val values = sequenceOf(true, false)
}
