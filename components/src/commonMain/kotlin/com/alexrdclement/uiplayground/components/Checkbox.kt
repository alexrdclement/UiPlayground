package com.alexrdclement.uiplayground.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import com.alexrdclement.uiplayground.theme.PlaygroundTheme

@Composable
fun Checkbox(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
    Button(
        style = ButtonStyle.Borderless,
        onClick = { onCheckedChange(isChecked) },
        modifier = modifier.semantics(mergeDescendants = true) {
            role = Role.Checkbox
        },
        enabled = enabled,
        interactionSource = interactionSource,
    ) {
        Text(
            text = if (isChecked) "☑︎" else "☐",
            style = PlaygroundTheme.typography.titleLarge
        )
    }
}
