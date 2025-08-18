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

enum class NoiseColorMode {
    Monochrome,
    RandomColor,
    RandomColorFilterBlack,
}

/**
 * @param amount: Returns the amount of noise to apply between 0f and 1f where 0f is none and 1f is
 * the maximum amount.
 */
fun Modifier.noise(
    colorMode: NoiseColorMode = NoiseColorMode.Monochrome,
    amount: () -> Float,
): Modifier = this then ShaderElement(
    shader = createNoiseShader {
        setColorMode(colorMode)
        setAmount(amount())
    },
    traceLabel = "noise",
)

data class NoiseIndication(
    private val colorMode: NoiseColorMode = NoiseColorMode.Monochrome,
    private val amount: (Interaction) -> Float = { 0f },
    private val animationSpec: AnimationSpec<Float> = tween(),
) : IndicationNodeFactory {

    override fun create(interactionSource: InteractionSource): DelegatableNode {
        val amountAnimatable = Animatable(0f)

        return ShaderIndicationNode(
            shader = createNoiseShader {
                setColorMode(colorMode)
            },
            traceLabel = "noiseIndication",
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

interface NoiseShader : Shader {
    fun setColorMode(colorMode: NoiseColorMode)
    fun setAmount(amount: Float)
}

expect fun createNoiseShader(
    configure: NoiseShader.() -> Unit
): NoiseShader
