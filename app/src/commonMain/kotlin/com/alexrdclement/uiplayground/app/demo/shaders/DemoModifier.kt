package com.alexrdclement.uiplayground.app.demo.shaders

import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.unit.Dp
import com.alexrdclement.uiplayground.app.demo.shaders.DemoModifier.Blur
import com.alexrdclement.uiplayground.app.demo.shaders.DemoModifier.ColorInvert
import com.alexrdclement.uiplayground.app.demo.shaders.DemoModifier.ColorSplit
import com.alexrdclement.uiplayground.app.demo.shaders.DemoModifier.Noise
import com.alexrdclement.uiplayground.app.demo.shaders.DemoModifier.None
import com.alexrdclement.uiplayground.app.demo.shaders.DemoModifier.Pixelate
import com.alexrdclement.uiplayground.app.demo.shaders.DemoModifier.Warp
import com.alexrdclement.uiplayground.components.util.mapSaverSafe
import com.alexrdclement.uiplayground.shaders.ColorSplitMode
import com.alexrdclement.uiplayground.shaders.NoiseColorMode

sealed class DemoModifier(open val name: String) {
    data object None : DemoModifier(name = "None")

    data class Blur(
        val radius: Dp,
        val edgeTreatment: BlurredEdgeTreatment,
    ) : DemoModifier(name = "Blur")

    data class ColorInvert(
        val amount: Float,
    ) : DemoModifier(name = "Color Invert")

    data class ColorSplit(
        val xAmount: Float,
        val yAmount: Float,
        val colorMode: ColorSplitMode,
    ) : DemoModifier(name = "Color Split")

    data class Noise(
        val colorMode: NoiseColorMode,
        val amount: Float,
    ) : DemoModifier(name = "Noise")

    data class Pixelate(
        val subdivisions: Int,
    ) : DemoModifier(name = "Pixelate")

    data class Warp(
        val radius: Dp,
        val amount: Float,
    ) : DemoModifier(name = "Warp")
}

private enum class Type {
    None,
    Blur,
    ColorInvert,
    ColorSplit,
    Noise,
    Pixelate,
    Warp
}

private const val typeKey = "type"
private const val radiusKey = "radius"
private const val amountKey = "amount"
private const val xAmountKey = "xAmount"
private const val yAmountKey = "yAmount"
private const val colorModeKey = "colorMode"
private const val subdivisionsKey = "subdivisions"

val DemoModifierSaver = mapSaverSafe(
    save = {
        when (it) {
            is None -> mapOf(typeKey to Type.None.name)
            is Blur -> mapOf(
                typeKey to Type.Blur.name,
                radiusKey to it.radius.value,
            )
            is ColorInvert -> mapOf(
                typeKey to Type.ColorInvert.name,
                amountKey to it.amount,
            )
            is ColorSplit -> mapOf(
                typeKey to Type.ColorSplit.name,
                xAmountKey to it.xAmount,
                yAmountKey to it.yAmount,
                colorModeKey to it.colorMode.ordinal,
            )
            is Noise -> mapOf(
                typeKey to Type.Noise.name,
                colorModeKey to it.colorMode.ordinal,
                amountKey to it.amount,
            )
            is Pixelate -> mapOf(
                typeKey to Type.Pixelate.name,
                subdivisionsKey to it.subdivisions,
            )
            is Warp -> mapOf(
                typeKey to Type.Warp.name,
                radiusKey to it.radius.value,
                amountKey to it.amount,
            )
        }
    },
    restore = {
        when (Type.valueOf(it[typeKey] as String)) {
            Type.None -> None
            Type.Blur -> Blur(
                radius = Dp(it[radiusKey] as Float),
                edgeTreatment = BlurredEdgeTreatment.Unbounded, // Not saved/restored
            )
            Type.ColorInvert -> ColorInvert(
                amount = it[amountKey] as Float,
            )
            Type.ColorSplit -> ColorSplit(
                xAmount = it[xAmountKey] as Float,
                yAmount = it[yAmountKey] as Float,
                colorMode = ColorSplitMode.entries[(it[colorModeKey] as Int)],
            )
            Type.Noise -> Noise(
                colorMode = NoiseColorMode.entries[(it[colorModeKey] as Int)],
                amount = it[amountKey] as Float,
            )
            Type.Pixelate -> Pixelate(
                subdivisions = it[subdivisionsKey] as Int,
            )
            Type.Warp -> Warp(
                radius = Dp(it[radiusKey] as Float),
                amount = it[amountKey] as Float,
            )
        }
    }
)
