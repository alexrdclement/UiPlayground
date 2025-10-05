package com.alexrdclement.uiplayground.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

data class Typography(
    val headline: TextStyle,
    val display: TextStyle,
    val titleLarge: TextStyle,
    val titleMedium: TextStyle,
    val titleSmall: TextStyle,
    val bodySmall: TextStyle,
    val bodyMedium: TextStyle,
    val bodyLarge: TextStyle,
    val labelLarge: TextStyle,
    val labelMedium: TextStyle,
    val labelSmall: TextStyle,
)

object PlaygroundTypographyDefaults {
    val fontFamily = FontFamily.Monospace
}

val PlaygroundTypography = makePlaygroundTypography(
    fontFamily = PlaygroundTypographyDefaults.fontFamily,
)

fun makePlaygroundTypography(
    fontFamily: FontFamily = FontFamily.Monospace,
): Typography {
    return Typography(
        display = TextStyle(
            fontFamily = fontFamily,
            fontSize = 57.sp,
            lineHeight = 64.sp,
            letterSpacing = (-0.2).sp
        ),
        headline = TextStyle(
            fontFamily = fontFamily,
            fontSize = 32.sp,
            lineHeight = 40.sp,
            letterSpacing = 0.sp
        ),
        titleLarge = TextStyle(
            fontFamily = fontFamily,
            fontSize = 22.sp,
            lineHeight = 28.sp,
            letterSpacing = 0.sp
        ),
        titleMedium = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.2.sp
        ),
        titleSmall = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp
        ),
        labelLarge = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp
        ),
        labelMedium = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp
        ),
        labelSmall = TextStyle(
            fontFamily = fontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp
        ),
        bodyLarge = TextStyle(
            fontFamily = fontFamily,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = fontFamily,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.2.sp
        ),
        bodySmall = TextStyle(
            fontFamily = fontFamily,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.4.sp
        ),
    )
}

fun Typography.copy(
    token: TypographyToken,
    textStyle: TextStyle,
) = this.copy(
    display = if (token == TypographyToken.Display) textStyle else this.display,
    headline = if (token == TypographyToken.Headline) textStyle else this.headline,
    titleLarge = if (token == TypographyToken.TitleLarge) textStyle else this.titleLarge,
    titleMedium = if (token == TypographyToken.TitleMedium) textStyle else this.titleMedium,
    titleSmall = if (token == TypographyToken.TitleSmall) textStyle else this.titleSmall,
    bodyLarge = if (token == TypographyToken.BodyLarge) textStyle else this.bodyLarge,
    bodyMedium = if (token == TypographyToken.BodyMedium) textStyle else this.bodyMedium,
    bodySmall = if (token == TypographyToken.BodySmall) textStyle else this.bodySmall,
    labelLarge = if (token == TypographyToken.LabelLarge) textStyle else this.labelLarge,
    labelMedium = if (token == TypographyToken.LabelMedium) textStyle else this.labelMedium,
    labelSmall = if (token == TypographyToken.LabelSmall) textStyle else this.labelSmall,
)
