package com.alexrdclement.uiplayground.shaders

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.InteractionSource
import kotlinx.coroutines.launch

class ShaderIndicationNode<T: Shader>(
    shader: T,
    traceLabel: String,
    private val interactionSource: InteractionSource,
    private val onAttach: ShaderIndicationNode<T>.() -> Unit = {},
    private val onInteraction: ShaderIndicationNode<T>.(Interaction) -> Unit = {},
): ShaderNode<T>(
    shader = shader,
    traceLabel = traceLabel,
) {
    override fun onAttach() {
        super.onAttach()
        onAttach.invoke(this)
        coroutineScope.launch {
            interactionSource.interactions.collect { interaction ->
                onInteraction(interaction)
            }
        }
    }
}
