package com.alexrdclement.uiplayground.app.demo.components.core

import androidx.compose.runtime.Composable
import com.alexrdclement.uiplayground.theme.PlaygroundTheme

enum class TextStyle {
    Display,
    Headline,
    TitleLarge,
    TitleMedium,
    TitleSmall,
    BodyLarge,
    BodyMedium,
    BodySmall,
    LabelLarge,
    LabelMedium,
    LabelSmall,
}

@Composable
fun TextStyle.toCompose() = when (this) {
    TextStyle.Display -> PlaygroundTheme.typography.display
    TextStyle.Headline -> PlaygroundTheme.typography.headline
    TextStyle.TitleLarge -> PlaygroundTheme.typography.titleLarge
    TextStyle.TitleMedium -> PlaygroundTheme.typography.titleMedium
    TextStyle.TitleSmall -> PlaygroundTheme.typography.titleSmall
    TextStyle.BodyLarge -> PlaygroundTheme.typography.bodyLarge
    TextStyle.BodyMedium -> PlaygroundTheme.typography.bodyMedium
    TextStyle.BodySmall -> PlaygroundTheme.typography.bodySmall
    TextStyle.LabelLarge -> PlaygroundTheme.typography.labelLarge
    TextStyle.LabelMedium -> PlaygroundTheme.typography.labelMedium
    TextStyle.LabelSmall -> PlaygroundTheme.typography.labelSmall
}
