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
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.shaders.preview.DemoCircle
import com.alexrdclement.uiplayground.shaders.preview.ShaderPreview

// Inspired by Rikin Marfatia's Grainy Gradients https://www.youtube.com/watch?v=soMl3k0mBx4

private var ShaderSource = """
uniform shader composable;
uniform float2 size;
uniform float amount;

// From https://thebookofshaders.com/10/
float noise(float2 fragCoord) {
    return fract(sin(dot(fragCoord.xy, float2(12.9898, 78.233))) * 43758.5453123);
}

half4 main(float2 fragCoord) {
    half4 color = composable.eval(fragCoord);

    float noise = noise(fragCoord);
    color.rgb *= 1 - noise * amount;
    
    return color;
}
"""

/**
 * @param amount: Returns the amount of noise to apply between 0f and 1f where 0f is none and 1f is
 * the maximum amount.
 */
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun Modifier.noise(
    amount: () -> Float,
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
        shader.setFloatUniform("amount", amount())
        renderEffect = RenderEffect
            .createRuntimeShaderEffect(shader, "composable")
            .asComposeRenderEffect()
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@ShaderPreview
@Composable
private fun Preview() {
    val range = 0f..1f
    var amount by remember { mutableStateOf(range.endInclusive / 2f) }
    Column {
        DemoCircle(
            modifier = Modifier
                .weight(1f)
                .noise(amount = { amount })
        )
        Slider(
            valueRange = range,
            value = amount,
            onValueChange = { amount = it },
            modifier = Modifier.padding(16.dp)
        )
    }
}
