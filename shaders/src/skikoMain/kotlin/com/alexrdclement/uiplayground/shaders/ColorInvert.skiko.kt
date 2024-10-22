package com.alexrdclement.uiplayground.shaders

import androidx.compose.ui.graphics.RenderEffect

private const val UniformShaderName = "composable"
private const val UniformAmount = "amount"

// SKSL
private var ShaderSource = """
uniform shader $UniformShaderName;
uniform float $UniformAmount;

half4 main(float2 fragCoord) {
    half4 color = composable.eval(fragCoord);
    return half4(abs(amount - color.r), abs(amount - color.g), abs(amount - color.b), color.a);
}
"""

actual fun createColorInvertShader(
    configure: ColorInvertShader.() -> Unit,
): ColorInvertShader {
    return ColorInvertShaderImpl(
        configure = configure,
    )
}

class ColorInvertShaderImpl(
    configure: ColorInvertShader.() -> Unit
) : ColorInvertShader {

    private val control = createShaderControl(ShaderSource, UniformShaderName, configure = { configure() })

    override fun setAmount(amount: Float) {
        control.setFloatUniform(UniformAmount, amount)
    }

    override fun createRenderEffect(): RenderEffect? {
        return control.createRenderEffect()
    }
}
