package dev.hyuwah.imusic.features.search.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RecentSearchQueries(
    modifier: Modifier = Modifier,
    searchHistories: List<String>,
    onItemClick: (query: String) -> Unit,
    onRemoveItem: (query: String) -> Unit,
    onClearAll: () -> Unit
) {
    if (searchHistories.isNotEmpty()) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 8.dp)
        ) {
            Text(text = "Recent Search", style = MaterialTheme.typography.titleSmall)
            IconButton(onClick = { onClearAll() }) {
                Icon(imageVector = Icons.Default.DeleteSweep, contentDescription = null)
            }
        }
        LazyColumn {
            items(searchHistories) { query ->
                ListItem(
                    headlineContent = {
                        Text(text = query)
                    },
                    leadingContent = {
                        Icon(imageVector = Icons.Default.History, contentDescription = null)
                    },
                    trailingContent = {
                        IconButton(onClick = { onRemoveItem(query) }) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                        }
                    },
                    modifier = Modifier.clickable {
                        onItemClick(query)
                    })
            }
        }
    }
}