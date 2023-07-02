package com.alexrdclement.uiplayground.demo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import com.alexrdclement.uiplayground.shaders.DemoModifier
import com.alexrdclement.uiplayground.util.UiPlaygroundPreview


@Composable
fun ControlBar(
    demoSubject: DemoSubject,
    demoModifier: DemoModifier,
    onSubjectSelected: (DemoSubject) -> Unit,
    onModifierSelected: (DemoModifier) -> Unit,
) {
    var isSubjectDropdownExpanded by remember { mutableStateOf(false) }
    var isModifierDropdownExpanded by remember { mutableStateOf(false) }

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ){
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
                DemoModifier.values().forEach {
                    DropdownMenuItem(
                        text = { androidx.compose.material3.Text(text = it.name) },
                        onClick = { onModifierSelected(it) }
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
        var demoSubject by remember { mutableStateOf(DemoSubject.Circle) }
        var demoModifier by remember { mutableStateOf(DemoModifier.Blur) }

        var isSubjectDropdownExpanded by remember { mutableStateOf(true) }
        var isModifierDropdownExpanded by remember { mutableStateOf(true) }

        ControlBar(
            demoSubject = demoSubject,
            demoModifier = demoModifier,
            onSubjectSelected = {
                demoSubject = it
                isSubjectDropdownExpanded = false
            },
            onModifierSelected = {
                demoModifier = it
                isModifierDropdownExpanded = false
            },
        )
    }
}
