package com.kaushikkumardevlab.kmptvshowsexplorer.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun CommonCategoriesScreen(
    viewModel: ShowListViewModel,
    onShowClick: (Int) -> Unit
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Categories") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.categories) { category ->
                    FilterChip(
                        selected = state.selectedCategory == category,
                        onClick = { viewModel.selectCategory(category) },
                        label = { Text(category) },
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }

            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (state.shows.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No shows found for this category")
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
