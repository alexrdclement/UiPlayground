package com.alexrdclement.uiplayground.shaders

import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.demo.subject.DemoCircle
import com.alexrdclement.uiplayground.util.UiPlaygroundPreview

// Inspired by
// - Romain Guy on Coding with the Italians: https://www.youtube.com/watch?v=s5RibxKdo-o
// - Rikin Marfatia video: https://www.youtube.com/watch?v=hjJesq71UXc

private var ShaderSource = """
uniform shader composable;
uniform float2 size;
uniform float xAmount;
uniform float yAmount;

half4 main(float2 fragCoord) {
    float xDisplacement = 1.0 - size.x * xAmount;
    float yDisplacement = 1.0 - size.y * yAmount;
    half4 color = composable.eval(fragCoord);
    return half4(
        composable.eval(float2(fragCoord.x - xDisplacement, fragCoord.y - yDisplacement)).r,
        color.g,
        composable.eval(float2(fragCoord.x + xDisplacement, fragCoord.y + yDisplacement)).b,
        color.a
    );
}
"""

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun Modifier.chromaticAberration(
    xAmount: () -> Float,
    yAmount: () -> Float,
): Modifier = composed {
    val shader = remember(ShaderSource) { RuntimeShader(ShaderSource) }

    this.onSizeChanged {
        shader.setFloatUniform(
            "size",
            it.width.toFloat(),
            it.height.toFloat()
        )
    }.graphicsLayer {
        clip = true
        shader.setFloatUniform("xAmount", xAmount())
        shader.setFloatUniform("yAmount", yAmount())
        renderEffect = RenderEffect
            .createRuntimeShaderEffect(shader, "composable")
            .asComposeRenderEffect()
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview
@Composable
private fun Preview() {
    UiPlaygroundPreview {
        val range = -1f..1f
        var xAmount by remember { mutableStateOf(range.endInclusive / 2f) }
        var yAmount by remember { mutableStateOf(range.endInclusive / 2f) }
        Column {
            DemoCircle(
                modifier = Modifier
                    .weight(1f)
                    .chromaticAberration(
                        xAmount = { xAmount },
                        yAmount = { yAmount }
                    )
            )
            Slider(
                valueRange = range,
                value = xAmount,
                onValueChange = { xAmount = it },
                modifier = Modifier.padding(16.dp)
            )
            Slider(
                valueRange = range,
                value = yAmount,
                onValueChange = { yAmount = it },
                modifier = Modifier.padding(16.dp)
            )
        }

    }
}
