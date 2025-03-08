package com.alexrdclement.uiplayground.app.demo.control

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.alexrdclement.uiplayground.app.demo.shaders.DemoModifier
import com.alexrdclement.uiplayground.app.demo.subject.DemoSubject
import com.alexrdclement.uiplayground.app.preview.UiPlaygroundPreview
import com.alexrdclement.uiplayground.shaders.ColorSplitMode

@Preview
@Composable
private fun Preview() {
    UiPlaygroundPreview {
        var demoSubject by remember { mutableStateOf(DemoSubject.Circle) }
        val demoModifiers = remember {
            mutableStateListOf<DemoModifier>(
                DemoModifier.ColorSplit(
                    xAmount = 0f,
                    yAmount = 0f,
                    colorMode = ColorSplitMode.RGB,
                )
            )
        }
        var demoModifierIndex by remember { mutableIntStateOf(0) }
        val demoModifier by remember(demoModifiers, demoModifierIndex) {
            derivedStateOf { demoModifiers[demoModifierIndex] }
        }

        var isSubjectDropdownExpanded by remember { mutableStateOf(true) }
        var isModifierDropdownExpanded by remember { mutableStateOf(true) }

        SubjectModifierBar(
            demoSubject = demoSubject,
            demoModifier = demoModifier,
            demoModifiers = demoModifiers,
            onSubjectSelected = {
                demoSubject = it
                isSubjectDropdownExpanded = false
            },
            onModifierSelected = {
                demoModifierIndex = it
                isModifierDropdownExpanded = false
            },
        )
    }
}
