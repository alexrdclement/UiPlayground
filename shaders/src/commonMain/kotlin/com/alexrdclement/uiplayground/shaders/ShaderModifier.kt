package com.alexrdclement.uiplayground.shaders

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.node.CompositionLocalConsumerModifierNode
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.LayoutAwareModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import androidx.compose.ui.node.currentValueOf
import androidx.compose.ui.platform.LocalGraphicsContext
import androidx.compose.ui.unit.IntSize
import com.alexrdclement.uiplayground.shaders.util.drawContentWithRenderEffect
import com.alexrdclement.uiplayground.core.trace.trace

data class ShaderElement(
    val shader: Shader,
    val traceLabel: String,
) : ModifierNodeElement<ShaderNode>() {
    override fun create() = ShaderNode(
        shader = shader,
        traceLabel = traceLabel,
    )
    override fun update(node: ShaderNode) {
        node.shader = shader
        node.traceLabel = traceLabel
    }
}

class ShaderNode(
    var shader: Shader,
    var traceLabel: String,
) : Modifier.Node(),
    DrawModifierNode,
    LayoutAwareModifierNode,
    CompositionLocalConsumerModifierNode {

    override fun onRemeasured(size: IntSize) {
        shader.onRemeasured(size.width, size.height)
    }

    override fun ContentDrawScope.draw() {
        trace(traceLabel) {
            val renderEffect = shader.createRenderEffect() ?: run {
                drawContent()
                return@trace
            }
            drawContentWithRenderEffect(renderEffect, currentValueOf(LocalGraphicsContext))
        }
    }
}
