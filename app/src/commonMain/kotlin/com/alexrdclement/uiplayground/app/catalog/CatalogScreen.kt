package com.alexrdclement.uiplayground.app.catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.app.preview.UiPlaygroundPreview
import com.alexrdclement.uiplayground.components.core.Button
import com.alexrdclement.uiplayground.components.core.Text
import com.alexrdclement.uiplayground.components.layout.Scaffold
import com.alexrdclement.uiplayground.components.layout.TopBar
import com.alexrdclement.uiplayground.components.navigation.BackNavigationButton
import com.alexrdclement.uiplayground.trace.ReportDrawn
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun <T : CatalogItem> CatalogScreen(
    items: List<T>,
    onItemClick: (T) -> Unit,
    title: String? = null,
    onNavigateBack: (() -> Unit)? = null,
    actions: @Composable () -> Unit = {}
) {
    ReportDrawn()

    Scaffold(
        topBar = {
            TopBar(
                title = title?.let {
                    { Text(title, style = PlaygroundTheme.typography.titleMedium) }
                },
                navButton = onNavigateBack?.let {
                    { BackNavigationButton(onNavigateBack) }
                },
                actions = actions,
            )
        },
        modifier = Modifier
            .displayCutoutPadding()
    ) { innerPadding ->
        LazyColumn(
            state = rememberLazyListState(),
            verticalArrangement = Arrangement.spacedBy(
                space = PlaygroundTheme.spacing.medium,
                alignment = Alignment.CenterVertically,
            ),
            contentPadding = innerPadding,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = PlaygroundTheme.spacing.medium)
        ) {
            items(
                items = items,
                key = { it.title }
            ) {
                Button(
                    onClick = { onItemClick(it) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(it.title)
                }
            }
            item {
                Spacer(modifier = Modifier.navigationBarsPadding())
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    UiPlaygroundPreview {
        CatalogScreen(
            items = MainCatalogItem.entries.toList(),
            onItemClick = {}
        )
    }
}

@Preview
@Composable
private fun WithNavPreview() {
    UiPlaygroundPreview {
        CatalogScreen(
            items = MainCatalogItem.entries.toList(),
            onItemClick = {},
            title = "Components",
            onNavigateBack = {},
        )
    }
}

