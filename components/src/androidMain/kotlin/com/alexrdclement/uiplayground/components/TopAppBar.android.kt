package com.alexrdclement.uiplayground.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
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
            TopAppBar(
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
            TopAppBar(
                title = {
                    Text("Title", style = PlaygroundTheme.typography.headline)
                },
                navButton = {
                    Button(
                        onClick = {},
                        modifier = Modifier.size(48.dp),
                    ) {
                        Image(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
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
            TopAppBar(
                title = {
                    Text("Title", style = PlaygroundTheme.typography.headline)
                },
                actions = {
                    Button(
                        onClick = {},
                        modifier = Modifier.size(48.dp),
                    ) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                }
            )
        }
    }
}
