package com.alexrdclement.uiplayground.app.catalog

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.alexrdclement.palette.components.core.Text
import com.alexrdclement.palette.components.layout.Scaffold
import com.alexrdclement.palette.components.layout.TopBar
import com.alexrdclement.palette.components.layout.catalog.Catalog
import com.alexrdclement.palette.components.layout.catalog.CatalogItem
import com.alexrdclement.palette.components.navigation.BackNavigationButton
import com.alexrdclement.palette.components.util.horizontalPaddingValues
import com.alexrdclement.palette.components.util.plus
import com.alexrdclement.palette.theme.PaletteTheme
import com.alexrdclement.trace.ReportDrawn

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
                    { Text(title, style = PaletteTheme.styles.text.titleMedium) }
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
                .padding(horizontal = PaletteTheme.spacing.medium)
        )
    }
}

@Preview
@Composable
private fun Preview() {
    PaletteTheme {
        CatalogScreen(
            items = MainCatalogItem.entries.toList(),
            onItemClick = {}
        )
    }
}

@Preview
@Composable
private fun WithNavPreview() {
    PaletteTheme {
        CatalogScreen(
            items = MainCatalogItem.entries.toList(),
            onItemClick = {},
            title = "Components",
            onNavigateBack = {},
        )
    }
}

