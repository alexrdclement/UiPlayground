package com.alexrdclement.uiplayground.app.demo.components.demo

import androidx.compose.runtime.Composable
import com.alexrdclement.uiplayground.theme.PlaygroundTheme

enum class TextStyle {
    Headline,
    Display,
    TitleSmall,
    TitleMedium,
    TitleLarge,
    BodyLarge,
    BodyMedium,
    BodySmall,
    LabelLarge,
    LabelMedium,
    LabelSmall,
}

@Composable
fun TextStyle.toCompose() = when (this) {
    TextStyle.Headline -> PlaygroundTheme.typography.headline
    TextStyle.Display -> PlaygroundTheme.typography.display
    TextStyle.TitleSmall -> PlaygroundTheme.typography.titleSmall
    TextStyle.TitleMedium -> PlaygroundTheme.typography.titleMedium
    TextStyle.TitleLarge -> PlaygroundTheme.typography.titleLarge
    TextStyle.BodyLarge -> PlaygroundTheme.typography.bodyLarge
    TextStyle.BodyMedium -> PlaygroundTheme.typography.bodyMedium
    TextStyle.BodySmall -> PlaygroundTheme.typography.bodySmall
    TextStyle.LabelLarge -> PlaygroundTheme.typography.labelLarge
    TextStyle.LabelMedium -> PlaygroundTheme.typography.labelMedium
    TextStyle.LabelSmall -> PlaygroundTheme.typography.labelSmall
}
