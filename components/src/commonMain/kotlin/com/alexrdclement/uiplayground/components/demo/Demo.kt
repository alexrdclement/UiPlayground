package com.alexrdclement.uiplayground.components.demo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.components.core.HorizontalDivider
import com.alexrdclement.uiplayground.components.core.VerticalDivider
import com.alexrdclement.uiplayground.components.demo.control.Control
import com.alexrdclement.uiplayground.components.demo.control.Controls
import com.alexrdclement.uiplayground.components.util.horizontalPaddingValues
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun Demo(
    modifier: Modifier = Modifier,
    controls: PersistentList<Control> = persistentListOf(),
    content: @Composable BoxWithConstraintsScope.() -> Unit,
) {
    BoxWithConstraints(
        modifier = modifier
            .padding(WindowInsets.safeDrawing.horizontalPaddingValues()),
    ) {
        if (minWidth < minHeight) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    this.content()
                }
                if (controls.isNotEmpty()) {
                    HorizontalDivider(modifier = Modifier.fillMaxWidth())
                    Controls(
                        controls = controls,
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 300.dp)
                            .verticalScroll(rememberScrollState())
                            .padding(PlaygroundTheme.spacing.medium)
                            .navigationBarsPadding(),
                    )
                }
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                ) {
                    this@BoxWithConstraints.content()
                }
                if (controls.isNotEmpty()) {
                    VerticalDivider(modifier = Modifier.fillMaxHeight())
                    Controls(
                        controls = controls,
                        modifier = Modifier
                            .fillMaxHeight()
                            .widthIn(max = 300.dp)
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = PlaygroundTheme.spacing.medium)
                            .navigationBarsPadding(),
                    )
                }
            }
        }
    }
}
