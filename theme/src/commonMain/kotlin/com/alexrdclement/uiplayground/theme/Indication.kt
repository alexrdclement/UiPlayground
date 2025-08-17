package com.alexrdclement.uiplayground.theme

import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.ui.Modifier
import androidx.compose.ui.node.DelegatableNode

val PlaygroundIndication = NoOpIndication()

class NoOpIndication(): IndicationNodeFactory {
    override fun create(interactionSource: InteractionSource): DelegatableNode {
        return NoOpIndicationNode()
    }

    override fun equals(other: Any?): Boolean {
        return other === this
    }

    override fun hashCode(): Int {
        return NoOpIndication::class.hashCode()
    }
}

class NoOpIndicationNode: Modifier.Node()
