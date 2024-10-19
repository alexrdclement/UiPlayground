package com.alexrdclement.uiplayground.app.catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.components.Button
import com.alexrdclement.uiplayground.components.Text
import com.alexrdclement.uiplayground.theme.PlaygroundTheme

@Composable
fun <T : CatalogItem> CatalogScreen(
    items: List<T>,
    onItemClick: (T) -> Unit,
) {
    // TODO
    // ReportDrawn()
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = PlaygroundTheme.spacing.medium,
            alignment = Alignment.CenterVertically,
        ),
        modifier = Modifier
            .fillMaxSize()
            .padding(PlaygroundTheme.spacing.small)
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
    ) {
        for (item in items) {
            Button(
                onClick = { onItemClick(item) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(item.title)
            }
        }
    }
}
