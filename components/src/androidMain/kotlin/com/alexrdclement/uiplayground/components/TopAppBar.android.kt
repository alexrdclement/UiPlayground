package com.alexrdclement.uiplayground.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.theme.PlaygroundTheme

@Preview
@Composable
private fun Preview() {
    PlaygroundTheme {
        Surface {
            TopBar(
                title = {
                    Text("Title", style = PlaygroundTheme.typography.headline)
                }
            )
        }
    }
}

@Preview
@Composable
private fun NavButtonPreview() {
    PlaygroundTheme {
        Surface {
            TopBar(
                title = {
                    Text("Title", style = PlaygroundTheme.typography.headline)
                },
                navButton = {
                    BackNavigationButton(onClick = {})
                }
            )
        }
    }
}

@Preview
@Composable
private fun ActionsPreview() {
    PlaygroundTheme {
        Surface {
            TopBar(
                title = {
                    Text("Title", style = PlaygroundTheme.typography.headline)
                },
                actions = {
                    Button(
                        onClick = {},
                        modifier = Modifier.size(48.dp),
                    ) {
                        Text("Action")
                    }
                }
            )
        }
    }
}
