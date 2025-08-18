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
 * @param subdivisions: Returns the number of additional adjacent pixels to include in each pixel
 * block.
 */
fun Modifier.pixelate(
    subdivisions: () -> Int,
): Modifier = this then ShaderElement(
    shader = createPixelateShader {
        setSubdivisions(subdivisions())
    },
    traceLabel = "pixelate",
)

data class PixelateIndication(
    private val subdivisions: (Interaction) -> Int = { 0 },
    private val animationSpec: AnimationSpec<Float> = tween(),
) : IndicationNodeFactory {

    override fun create(interactionSource: InteractionSource): DelegatableNode {
        val subdivisionsAnimatable = Animatable(0f)
        return ShaderIndicationNode(
            shader = createPixelateShader(),
            traceLabel = "pixelateIndication",
            interactionSource = interactionSource,
            onAttach = {
                coroutineScope.launch {
                    snapshotFlow { subdivisionsAnimatable.value }.collect { value ->
                        shader.setSubdivisions(value.toInt())
                        invalidateDraw()
                    }
                }
            },
            onInteraction = { interaction ->
                coroutineScope.launch {
                    subdivisionsAnimatable.animateTo(
                        targetValue = subdivisions(interaction).toFloat(),
                        animationSpec = animationSpec,
                    )
                }
            },
        )
    }
}

interface PixelateShader : Shader {
    fun setSubdivisions(subdivisions: Int)
}

expect fun createPixelateShader(
    configure: PixelateShader.() -> Unit = {},
): PixelateShader
