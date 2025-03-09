package com.alexrdclement.uiplayground.app.demo.control

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.alexrdclement.uiplayground.app.demo.shaders.DemoModifier
import com.alexrdclement.uiplayground.app.demo.subject.DemoSubject
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import kotlinx.collections.immutable.toPersistentList

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
                }.toPersistentList(),
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
                }.toPersistentList(),
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
