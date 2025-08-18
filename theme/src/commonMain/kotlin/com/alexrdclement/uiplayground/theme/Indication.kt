package com.alexrdclement.uiplayground.theme

import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.ui.Modifier
import androidx.compose.ui.node.DelegatableNode
import com.alexrdclement.uiplayground.shaders.ColorInvertIndication
import com.alexrdclement.uiplayground.shaders.ColorSplitIndication
import com.alexrdclement.uiplayground.shaders.ColorSplitMode
import com.alexrdclement.uiplayground.shaders.NoiseColorMode
import com.alexrdclement.uiplayground.shaders.NoiseIndication
import com.alexrdclement.uiplayground.shaders.PixelateIndication

enum class PlaygroundIndicationType {
    None,
    ColorInvert,
    ColorSplit,
    Noise,
    Pixelate,
}

val PlaygroundIndication: Indication = PlaygroundIndicationType.ColorSplit.toIndication()

fun Indication.toPlaygroundIndicationType(): PlaygroundIndicationType {
    return when (this) {
        is NoOpIndication -> PlaygroundIndicationType.None
        is ColorInvertIndication -> PlaygroundIndicationType.ColorInvert
        is ColorSplitIndication -> PlaygroundIndicationType.ColorSplit
        is NoiseIndication -> PlaygroundIndicationType.Noise
        is PixelateIndication -> PlaygroundIndicationType.Pixelate
        else -> throw IllegalArgumentException("Unknown indication type: $this")
    }
}

fun PlaygroundIndicationType.toIndication(): Indication = when (this) {
    PlaygroundIndicationType.None -> NoOpIndication
    PlaygroundIndicationType.ColorInvert -> ColorInvertIndication(
        amount = { interaction ->
            when (interaction) {
                is HoverInteraction.Enter -> .5f
                is PressInteraction.Press -> 1f
                else -> 0f
            }
        },
    )
    PlaygroundIndicationType.ColorSplit -> ColorSplitIndication(
        xAmount = { interaction ->
            when (interaction) {
                is HoverInteraction.Enter -> -0.02f
                is PressInteraction.Press -> 0.02f
                else -> 0f
            }
        },
        yAmount = { interaction ->
            when (interaction) {
                is HoverInteraction.Enter -> -0.02f
                is PressInteraction.Press -> 0.02f
                else -> 0f
            }
        },
        colorMode = ColorSplitMode.RGB,
    )
    PlaygroundIndicationType.Noise -> NoiseIndication(
        colorMode = NoiseColorMode.RandomColorFilterBlack,
        amount = { interaction ->
            when (interaction) {
                is HoverInteraction.Enter -> 0.25f
                is PressInteraction.Press -> 1f
                else -> 0f
            }
        },
    )
    PlaygroundIndicationType.Pixelate -> PixelateIndication(
        subdivisions = { interaction ->
            when (interaction) {
                is HoverInteraction.Enter -> 2
                is PressInteraction.Press -> 6
                else -> 0
            }
        },
    )
}

data object NoOpIndication: IndicationNodeFactory {
    override fun create(interactionSource: InteractionSource): DelegatableNode {
        return object : Modifier.Node() {}
    }
}
