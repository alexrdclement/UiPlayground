package com.alexrdclement.uiplayground.shaders

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.invalidateDraw
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

fun Modifier.warp(
    offset: () -> Offset,
    radius: () -> Dp,
    amount: () -> Float,
): Modifier = this then ShaderElement(
    shader = createWarpShader {
        setOffset(offset())
        setRadius(radius())
        setAmount(amount())
    },
    traceLabel = "warp",
)

data class WarpIndication(
    private val point: (Interaction) -> Offset,
    private val radius: (Interaction) -> Dp,
    private val amount: (Interaction) -> Float = { 0f },
    private val animationSpec: AnimationSpec<Float> = tween(),
) : IndicationNodeFactory {

    override fun create(interactionSource: InteractionSource): DelegatableNode {
        val radiusAnimatable = Animatable(0f)
        val amountAnimatable = Animatable(0f)

        return ShaderIndicationNode(
            shader = createWarpShader(),
            traceLabel = "warpIndication",
            interactionSource = interactionSource,
            onAttach = {
                coroutineScope.launch {
                    snapshotFlow { radiusAnimatable.value }.collect { value ->
                        shader.setRadius(value.dp)
                        invalidateDraw()
                    }
                }
                coroutineScope.launch {
                    snapshotFlow { amountAnimatable.value }.collect { value ->
                        shader.setAmount(value)
                        invalidateDraw()
                    }
                }
            },
            onInteraction = { interaction ->
                shader.setOffset(point(interaction))
                invalidateDraw()

                coroutineScope.launch {
                    radiusAnimatable.animateTo(
                        targetValue = radius(interaction).value,
                        animationSpec = animationSpec,
                    )
                }
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

interface WarpShader : Shader {
    fun setOffset(offset: Offset)
    fun setRadius(radius: Dp)
    fun setAmount(amount: Float)
}

expect fun createWarpShader(
    configure: WarpShader.() -> Unit = {},
): WarpShader
