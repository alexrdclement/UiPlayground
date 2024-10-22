package com.alexrdclement.uiplayground.shaders

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.shaders.preview.DemoCircle
import com.alexrdclement.uiplayground.shaders.preview.ShaderPreview
import org.intellij.lang.annotations.Language
import kotlin.math.roundToInt

// Inspired by Rikin Marfatia's Pixellate
// https://github.com/Rahkeen/ShaderPlayground/blob/main/app/src/main/java/co/rikin/shaderplayground/shaders/PixellateShader.kt

private const val UniformShaderName = "composable"
private const val UniformSizeName = "size"
private const val UniformSubdivisionsName = "subdivisions"

@Language("AGSL")
private var ShaderSource = """
uniform shader $UniformShaderName;
uniform float2 $UniformSizeName;
uniform float $UniformSubdivisionsName;

half4 main(float2 fragCoord) {
    float2 newCoord = fragCoord;
    newCoord.x -= mod(fragCoord.x, subdivisions + 1);
    newCoord.y -= mod(fragCoord.y, subdivisions + 1);
    return composable.eval(newCoord);
}
"""

actual fun createPixelateShader(
    configure: PixelateShader.() -> Unit
): PixelateShader {
    return PixelateShaderImpl(configure)
}

class PixelateShaderImpl(
    configure: PixelateShader.() -> Unit
): PixelateShader {
    private val control = createShaderControl(ShaderSource, UniformShaderName, configure = { configure() })

    override fun createRenderEffect(): RenderEffect? {
        return control.createRenderEffect()
    }

    override fun setSubdivisions(subdivisions: Int) {
        control.setFloatUniform(UniformSubdivisionsName, subdivisions.toFloat())
    }

    override fun onRemeasured(width: Int, height: Int) {
        control.setFloatUniform(UniformSizeName, width.toFloat(), height.toFloat())
    }
}

@ShaderPreview
@Composable
private fun Preview() {
    val range = 0f..100f
    var amount by remember { mutableStateOf(range.endInclusive / 2f) }
    Column {
        DemoCircle(
            modifier = Modifier
                .weight(1f)
                .pixelate(subdivisions = { amount.roundToInt() })
        )
        Slider(
            valueRange = range,
            value = amount,
            onValueChange = { amount = it },
            modifier = Modifier.padding(16.dp)
        )
    }
}
