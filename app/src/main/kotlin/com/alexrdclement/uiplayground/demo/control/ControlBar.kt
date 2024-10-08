package com.alexrdclement.uiplayground.demo.control

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.demo.subject.DemoSubject
import com.alexrdclement.uiplayground.shaders.ColorSplitMode
import com.alexrdclement.uiplayground.demo.shaders.DemoModifier
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import com.alexrdclement.uiplayground.ui.preview.UiPlaygroundPreview

@Composable
fun ControlBar(
    controls: List<Control>,
    demoSubject: DemoSubject,
    demoModifier: DemoModifier,
    demoModifiers: List<DemoModifier>,
    onSubjectSelected: (DemoSubject) -> Unit,
    onModifierSelected: (index: Int) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(modifier) {
        for (control in controls) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(PlaygroundTheme.spacing.medium)
            ) {
                when (control) {
                    is Control.Slider -> SliderControl(control = control)
                    is Control.Dropdown<*> -> DropdownControl(control = control)
                    is Control.Toggle -> ToggleControl(control = control)
                }
            }
        }

        SubjectModifierBar(
            demoSubject = demoSubject,
            demoModifier = demoModifier,
            demoModifiers = demoModifiers,
            onSubjectSelected = onSubjectSelected,
            onModifierSelected = onModifierSelected
        )
    }
}

@Composable
fun SubjectModifierBar(
    demoSubject: DemoSubject,
    demoModifier: DemoModifier,
    demoModifiers: List<DemoModifier>,
    onSubjectSelected: (DemoSubject) -> Unit,
    onModifierSelected: (index: Int) -> Unit,
) {
    val subjectControl by remember(demoSubject) {
        mutableStateOf(
            Control.Dropdown(
                name = "",
                values = DemoSubject.entries.map {
                    Control.Dropdown.DropdownItem(name = it.name, value = it)
                },
                selectedIndex = DemoSubject.entries.indexOf(demoSubject),
                onValueChange = { onSubjectSelected(DemoSubject.entries[it]) }
            )
        )
    }
    val modifierControl by remember(demoModifier) {
        mutableStateOf(
            Control.Dropdown(
                name = "",
                values = demoModifiers.map {
                    Control.Dropdown.DropdownItem(name = it.name, value = it)
                },
                selectedIndex = demoModifiers.indexOf(demoModifier),
                onValueChange = { onModifierSelected(it) }
            )
        )
    }

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(PlaygroundTheme.spacing.medium)
    ) {
        DropdownControl(
            control = subjectControl,
            includeTitle = false,
            modifier = Modifier.semantics {
                contentDescription = "Select subject"
            },
        )
        DropdownControl(
            control = modifierControl,
            includeTitle = false,
            modifier = Modifier.semantics {
                contentDescription = "Select modifier"
            },
        )
    }
}

@Preview
@Composable
private fun Preview() {
    UiPlaygroundPreview {
        val controls = listOf(
            Control.Slider(
                name = "Amount",
                value = 0.5f,
                onValueChange = {},
            ),
            Control.Slider(
                name = "Amount 2",
                value = 0.5f,
                onValueChange = {},
            )
        )

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
        var demoModifierIndex by remember { mutableStateOf(0) }
        val demoModifier by remember(demoModifiers, demoModifierIndex) {
            derivedStateOf { demoModifiers[demoModifierIndex] }
        }

        var isSubjectDropdownExpanded by remember { mutableStateOf(true) }
        var isModifierDropdownExpanded by remember { mutableStateOf(true) }

        ControlBar(
            controls = controls,
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
