package com.alexrdclement.uiplayground.catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.ui.preview.UiPlaygroundPreview

@Composable
fun <T : CatalogItem> CatalogScreen(
    items: List<T>,
    onItemClick: (T) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.CenterVertically,
        ),
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
    ) {
        for (item in items) {
            OutlinedButton(
                onClick = { onItemClick(item) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(item.title)
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    UiPlaygroundPreview {
        CatalogScreen(
            items = MainCatalogItem.values().toList(),
            onItemClick = {}
        )
    }
}
