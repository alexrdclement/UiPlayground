package com.alexrdclement.uiplayground.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.shaders.DemoModifier
import com.alexrdclement.uiplayground.util.UiPlaygroundPreview


@Composable
fun ControlBar(
    controls: List<Control>,
    demoSubject: DemoSubject,
    demoModifier: DemoModifier,
    demoModifiers: List<DemoModifier>,
    onSubjectSelected: (DemoSubject) -> Unit,
    onModifierSelected: (index: Int) -> Unit,
) {

    Column {
        for (control in controls) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                when (control) {
                    is Control.Slider -> SliderControl(control = control)
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
    var isSubjectDropdownExpanded by remember { mutableStateOf(false) }
    var isModifierDropdownExpanded by remember { mutableStateOf(false) }

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Box {
            ControlBarItem(
                name = demoSubject.name,
                onClick = {
                    isSubjectDropdownExpanded = true
                }
            )

            DropdownMenu(
                expanded = isSubjectDropdownExpanded,
                onDismissRequest = { isSubjectDropdownExpanded = false },
            ) {
                DemoSubject.values().forEach {
                    DropdownMenuItem(
                        text = { androidx.compose.material3.Text(text = it.name) },
                        onClick = {
                            onSubjectSelected(it)
                        }
                    )
                }
            }
        }
        Box {
            ControlBarItem(
                name = demoModifier.name,
                onClick = {
                    isModifierDropdownExpanded = true
                }
            )

            DropdownMenu(
                expanded = isModifierDropdownExpanded,
                onDismissRequest = { isModifierDropdownExpanded = false },
            ) {
                demoModifiers.forEachIndexed { index, demoModifier ->
                    DropdownMenuItem(
                        text = { androidx.compose.material3.Text(text = demoModifier.name) },
                        onClick = { onModifierSelected(index) }
                    )
                }
            }
        }
    }
}

@Composable
fun ControlBarItem(
    name: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
       androidx.compose.material3.Text(text = name)
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
                DemoModifier.Blur(radius = 2.dp)
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
