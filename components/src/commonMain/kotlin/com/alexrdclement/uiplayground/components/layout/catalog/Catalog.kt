package com.alexrdclement.uiplayground.components.layout.catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.components.core.Button
import com.alexrdclement.uiplayground.components.core.Text
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import com.alexrdclement.uiplayground.theme.styles.ButtonStyleToken
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun <T : CatalogItem> Catalog(
    items: List<T>,
    onItemClick: (T) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyColumn(
        state = rememberLazyListState(),
        verticalArrangement = Arrangement.spacedBy(
            space = PlaygroundTheme.spacing.medium,
            alignment = Alignment.CenterVertically,
        ),
        contentPadding = contentPadding,
        modifier = modifier
            .fillMaxSize()
    ) {
        items(
            items = items,
            key = { it.title }
        ) {
            Button(
                style = ButtonStyleToken.Secondary,
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


private enum class MainCatalogItem : CatalogItem {
    Components,
    Experiments,
    Shaders,
    ;

    override val title = this.name
}

@Preview
@Composable
private fun Preview() {
    PlaygroundTheme {
        Catalog(
            items = MainCatalogItem.entries.toList(),
            onItemClick = {}
        )
    }
}
