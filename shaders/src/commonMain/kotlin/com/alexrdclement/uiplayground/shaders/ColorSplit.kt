package com.alexrdclement.uiplayground.shaders

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.invalidateDraw
import kotlinx.coroutines.launch

enum class ColorSplitMode {
    RGB,
    CMYK,
    RGBInverted,
}

/**
 * @param xAmount: Returns the amount of shift to apply on the X axis where 0f applies none
 * and 1f offsets by the full width of the target.
 * @param xAmount: Returns the amount of shift to apply on the Y axis where 0f applies none
 * and 1f offsets by the full height of the target.
 * @param colorMode: The output color space.
 */
fun Modifier.colorSplit(
    xAmount: () -> Float = { 0f },
    yAmount: () -> Float = { 0f },
    colorMode: () -> ColorSplitMode = { ColorSplitMode.RGB },
): Modifier = this then ShaderElement(
    shader = createColorSplitShader {
        setXAmount(xAmount())
        setYAmount(yAmount())
        setColorMode(colorMode())
    },
    traceLabel = "colorSplit",
)

data class ColorSplitIndication(
    private val xAmount: (Interaction) -> Float = { 0f },
    private val yAmount: (Interaction) -> Float = { 0f },
    private val colorMode: ColorSplitMode = ColorSplitMode.RGB,
    private val animationSpec: AnimationSpec<Float> = tween(),
) : IndicationNodeFactory {

    override fun create(interactionSource: InteractionSource): DelegatableNode {
        val xAmountAnimatable = Animatable(0f)
        val yAmountAnimatable = Animatable(0f)

        return ShaderIndicationNode(
            shader = createColorSplitShader {
                setColorMode(colorMode)
            },
            traceLabel = "colorSplitIndication",
            interactionSource = interactionSource,
            onAttach = {
                coroutineScope.launch {
                    snapshotFlow { xAmountAnimatable.value }.collect { value ->
                        shader.setXAmount(value)
                        invalidateDraw()
                    }
                }
                coroutineScope.launch {
                    snapshotFlow { yAmountAnimatable.value }.collect { value ->
                        shader.setYAmount(value)
                        invalidateDraw()
                    }
                }
            },
            onInteraction = { interaction ->
                val (xAmountValue, yAmountValue) = xAmount(interaction) to yAmount(interaction)
                coroutineScope.launch {
                    xAmountAnimatable.animateTo(
                        targetValue = xAmountValue,
                        animationSpec = animationSpec,
                    )
                }
                coroutineScope.launch {
                    yAmountAnimatable.animateTo(
                        targetValue = yAmountValue,
                        animationSpec = animationSpec,
                    )
                }
            },
        )
    }
}

interface ColorSplitShader : Shader {
    fun setXAmount(xAmount: Float)
    fun setYAmount(yAmount: Float)
    fun setColorMode(mode: ColorSplitMode)
}

expect fun createColorSplitShader(
    configure: ColorSplitShader.() -> Unit
): ColorSplitShader
