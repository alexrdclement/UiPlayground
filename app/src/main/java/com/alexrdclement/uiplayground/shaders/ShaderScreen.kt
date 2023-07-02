package com.alexrdclement.uiplayground.shaders

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.demo.Circle
import com.alexrdclement.uiplayground.demo.ControlBar
import com.alexrdclement.uiplayground.demo.DemoSubject
import com.alexrdclement.uiplayground.ui.theme.UiPlaygroundTheme
import com.alexrdclement.uiplayground.demo.Text as DemoText

enum class DemoModifier {
    None,
    Blur,
    ChromaticAberration,
}

@Composable
fun ShaderScreen() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
        // Bummer
        return
    }

    var demoSubject by remember { mutableStateOf(DemoSubject.Circle) }
    var demoModifier by remember { mutableStateOf(DemoModifier.None) }

    Surface(
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier.weight(1f)
            ) {
                val modifier = when (demoModifier) {
                    DemoModifier.None -> Modifier
                    DemoModifier.Blur -> Modifier.blur()
                    DemoModifier.ChromaticAberration -> Modifier.chromaticAberration()
                }.border(
                    1.dp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                when (demoSubject) {
                    DemoSubject.Circle -> Circle(modifier = modifier)
                    DemoSubject.Text -> DemoText(modifier = modifier)
                }
            }

            ControlBar(
                demoSubject = demoSubject,
                demoModifier = demoModifier,
                onSubjectSelected = {
                    demoSubject = it
                },
                onModifierSelected = {
                    demoModifier = it
                },
            )
        }
    }
}


@Preview
@Composable
private fun Preview() {
    UiPlaygroundTheme {
        ShaderScreen()
    }
}
