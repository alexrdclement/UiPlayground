package com.alexrdclement.uiplayground.theme.preview

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.alexrdclement.uiplayground.theme.PlaygroundTypography

class TextStylePreviewParameterProvider : PreviewParameterProvider<Pair<String, TextStyle>> {
    override val values = sequenceOf(
        "headline" to PlaygroundTypography.headline,
        "display" to PlaygroundTypography.display,
        "titleLarge" to PlaygroundTypography.titleLarge,
        "titleMedium" to PlaygroundTypography.titleMedium,
        "titleSmall" to PlaygroundTypography.titleSmall,
        "labelLarge" to PlaygroundTypography.labelLarge,
        "labelMedium" to PlaygroundTypography.labelMedium,
        "labelSmall" to PlaygroundTypography.labelSmall,
        "bodyLarge" to PlaygroundTypography.bodyLarge,
        "bodyMedium" to PlaygroundTypography.bodyMedium,
        "bodySmall" to PlaygroundTypography.bodySmall,
    )
}
