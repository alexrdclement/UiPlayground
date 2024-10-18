package com.alexrdclement.uiplayground.shaders.util

import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.GraphicsContext
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.nativeCanvas

actual fun ContentDrawScope.drawContentWithRenderEffect(
    renderEffect: RenderEffect,
    graphicsContext: GraphicsContext,
) {
    graphicsContext.useGraphicsLayer {
        clip = true
        this.renderEffect = renderEffect

        val canvas = this@drawContentWithRenderEffect.drawContext.canvas
        if (canvas.nativeCanvas.isHardwareAccelerated) {
            record { this@drawContentWithRenderEffect.drawContent() }
            drawLayer(this)
        } else {
            // Bad but hopefully temporary until software workaround added to GraphicsLayer
            val softwareLayerPaint = Paint()
            val clipPath = Path()
            drawLayerSoftware(this, canvas, softwareLayerPaint, clipPath)
        }
    }
}

/*
 * Software renderer for Paparazzi tests.
 * Copied and modified from androidx.compose.ui.platform.GraphicsLayerOwnerLayer.android.kt
 * Temp until https://android-review.googlesource.com/c/platform/frameworks/support/+/3094568 is fixed
 */
private fun ContentDrawScope.drawLayerSoftware(
    graphicsLayer: GraphicsLayer,
    canvas: Canvas,
    softwareLayerPaint: Paint,
    clipPath: Path,
) {
    // TODO ideally there should be some solution for drawing a layer on a software
    //  accelerated canvas built in right into GraphicsLayer, as this workaround is not
    //  solving all the use cases. For example, some one can use layers directly via
    //        drawWithContent {
    //            layer.record {
    //                this@drawWithContent.drawContent()
    //            }
    //            drawLayer(layer)
    //        }
    //  and if someone would try to draw the whole ComposeView on software accelerated
    //  canvas it will just crash saying RenderNodes can't be drawn into this canvas.
    //  This issue is tracked in b/333866398
    val left = graphicsLayer.topLeft.x.toFloat()
    val top = graphicsLayer.topLeft.y.toFloat()
    val right = left + size.width
    val bottom = top + size.height
    // If there is alpha applied, we must render into an offscreen buffer to
    // properly blend the contents of this layer against the background content
    if (graphicsLayer.alpha < 1.0f) {
        val paint = softwareLayerPaint.apply { alpha = graphicsLayer.alpha }
        canvas.nativeCanvas.saveLayer(left, top, right, bottom, paint.asFrameworkPaint())
    } else {
        canvas.save()
    }
    // If we are software rendered we must translate the canvas based on the offset provided
    // in the move call which operates directly on the RenderNode
    canvas.translate(left, top)

    // AC: Matrix transforms not supported
//    canvas.concat(getMatrix())

    if (graphicsLayer.clip) {
        clipManually(graphicsLayer, canvas, clipPath)
    }
    drawContent()
    canvas.restore()
}

private fun clipManually(graphicsLayer: GraphicsLayer, canvas: Canvas, path: Path) {
    if (graphicsLayer.clip) {
        when (val outline = graphicsLayer.outline) {
            is Outline.Rectangle -> {
                canvas.clipRect(outline.rect)
            }
            is Outline.Rounded -> {
                path.reset()
                path.addRoundRect(outline.roundRect)
                canvas.clipPath(path)
            }
            is Outline.Generic -> {
                canvas.clipPath(outline.path)
            }
        }
    }
}
