package com.kaushikkumardevlab.kmptvshowsexplorer.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaushikkumardevlab.kmptvshowsexplorer.presentation.viewmodel.ShowListViewModel
import com.kaushikkumardevlab.kmptvshowsexplorer.ui.component.ShowItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonSearchScreen(
    viewModel: ShowListViewModel,
    onShowClick: (Int) -> Unit
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Search Shows") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            OutlinedTextField(
                value = state.searchQuery,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Search by name...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true,
                shape = RoundedCornerShape(28.dp)
            )

            if (state.isLoading && state.shows.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (state.shows.isEmpty() && state.searchQuery.isNotBlank()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No shows found for \"${state.searchQuery}\"")
                }
            } else if (state.searchQuery.isBlank()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Start typing to discover shows")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(state.shows, key = { it.id }) { show ->
                        ShowItem(
                            show = show,
                            onClick = { onShowClick(show.id) }
                        )
                    }
                }
            }
        }
    }
}
