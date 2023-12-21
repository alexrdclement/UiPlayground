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

// Inspired by
// - Romain Guy on Coding with the Italians: https://www.youtube.com/watch?v=s5RibxKdo-o
// - Rikin Marfatia video: https://www.youtube.com/watch?v=hjJesq71UXc

enum class ChromaticAberrationColorMode {
    RGB,
    CMYK,
    RGBInverted,
}

private var ShaderSource = """
uniform shader composable;
uniform float2 size;
uniform float xAmount;
uniform float yAmount;
uniform int colorMode;

half4 CMYKtoRGB(half4 cmyk) {
    float c = cmyk.x;
    float m = cmyk.y;
    float y = cmyk.z;
    float k = cmyk.w;

    float invK = 1.0 - k;
    float r = 1.0 - min(1.0, c * invK + k);
    float g = 1.0 - min(1.0, m * invK + k);
    float b = 1.0 - min(1.0, y * invK + k);
    return clamp(half4(r, g, b, 1.0), 0.0, 1.0);
}

half4 RGBtoCMYK(half4 rgb) {
    float r = rgb.r;
    float g = rgb.g;
    float b = rgb.b;
    float k = min(1.0 - r, min(1.0 - g, 1.0 - b));
    float3 cmy = float3(0.0);
    float invK = 1.0 - k;
    if (invK != 0.0) {
        cmy.x = (1.0 - r - k) / invK;
        cmy.y = (1.0 - g - k) / invK;
        cmy.z = (1.0 - b - k) / invK;
    }
    return clamp(half4(cmy, k), 0.0, 1.0);
}

half4 main(float2 fragCoord) {
    float xDisplacement = 1.0 - size.x * xAmount;
    float yDisplacement = 1.0 - size.y * yAmount;
    half4 color = composable.eval(fragCoord);
    half4 displacedColor = half4(
        composable.eval(float2(fragCoord.x - xDisplacement, fragCoord.y - yDisplacement)).r,
        color.g,
        composable.eval(float2(fragCoord.x + xDisplacement, fragCoord.y + yDisplacement)).b,
        color.a
    );
    if (colorMode == 1) {
        return RGBtoCMYK(displacedColor);
    } else if (colorMode == 2) {
        return half4(1.0) - displacedColor;
    }
    return displacedColor;
}
"""


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun Modifier.chromaticAberration(
    xAmount: () -> Float,
    yAmount: () -> Float,
    colorMode: () -> ChromaticAberrationColorMode = { ChromaticAberrationColorMode.RGB },
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
        shader.setIntUniform("colorMode", colorMode().ordinal)
        renderEffect = RenderEffect
            .createRuntimeShaderEffect(shader, "composable")
            .asComposeRenderEffect()
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@ShaderPreview
@Composable
private fun Preview() {
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
