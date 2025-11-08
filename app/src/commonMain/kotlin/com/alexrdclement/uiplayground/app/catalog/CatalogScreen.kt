package com.alexrdclement.uiplayground.app.catalog

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.components.core.Text
import com.alexrdclement.uiplayground.components.layout.Scaffold
import com.alexrdclement.uiplayground.components.layout.TopBar
import com.alexrdclement.uiplayground.components.layout.catalog.Catalog
import com.alexrdclement.uiplayground.components.layout.catalog.CatalogItem
import com.alexrdclement.uiplayground.components.navigation.BackNavigationButton
import com.alexrdclement.uiplayground.components.util.horizontalPaddingValues
import com.alexrdclement.uiplayground.components.util.plus
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import com.alexrdclement.uiplayground.trace.ReportDrawn
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
    ) { innerPadding ->
        Catalog(
            items = items,
            onItemClick = onItemClick,
            contentPadding = innerPadding.plus(WindowInsets.safeDrawing.horizontalPaddingValues()),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = PlaygroundTheme.spacing.medium)
        )
    }
}

@Preview
@Composable
private fun Preview() {
    PlaygroundTheme {
        CatalogScreen(
            items = MainCatalogItem.entries.toList(),
            onItemClick = {}
        )
    }
}

@Preview
@Composable
private fun WithNavPreview() {
    PlaygroundTheme {
        CatalogScreen(
            items = MainCatalogItem.entries.toList(),
            onItemClick = {},
            title = "Components",
            onNavigateBack = {},
        )
    }
}

