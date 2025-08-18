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

/**
 * @param amount: Returns the amount of color inversion to apply where 0f applies none and 1f fully
 * inverts the color.
 */
fun Modifier.colorInvert(
    amount: () -> Float,
): Modifier = this then ShaderElement(
    shader = createColorInvertShader {
        setAmount(amount())
    },
    traceLabel = "colorInvert",
)

data class ColorInvertIndication(
    private val amount: (Interaction) -> Float = { 0f },
    private val animationSpec: AnimationSpec<Float> = tween()
) : IndicationNodeFactory {

    override fun create(interactionSource: InteractionSource): DelegatableNode {
        val amountAnimatable = Animatable(0f)
        return ShaderIndicationNode(
            shader = createColorInvertShader(),
            traceLabel = "colorInvertIndication",
            interactionSource = interactionSource,
            onAttach = {
                coroutineScope.launch {
                    snapshotFlow { amountAnimatable.value }.collect { value ->
                        shader.setAmount(value)
                        invalidateDraw()
                    }
                }
            },
            onInteraction = { interaction ->
                coroutineScope.launch {
                    amountAnimatable.animateTo(
                        targetValue = amount(interaction),
                        animationSpec = animationSpec,
                    )
                }
            },
        )
    }
}

interface ColorInvertShader : Shader {
    fun setAmount(amount: Float)
}

expect fun createColorInvertShader(
    configure: ColorInvertShader.() -> Unit = {},
): ColorInvertShader
