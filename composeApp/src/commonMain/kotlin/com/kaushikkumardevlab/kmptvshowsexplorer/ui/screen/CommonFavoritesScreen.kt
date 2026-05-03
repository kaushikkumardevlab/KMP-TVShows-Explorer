package com.kaushikkumardevlab.kmptvshowsexplorer.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaushikkumardevlab.kmptvshowsexplorer.presentation.viewmodel.FavoriteShowsViewModel
import com.kaushikkumardevlab.kmptvshowsexplorer.ui.component.ShowItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonFavoritesScreen(
    viewModel: FavoriteShowsViewModel,
    onShowClick: (Int) -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadFavorites()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Favorites") })
        }
    ) { paddingValues ->
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (state.isEmpty) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No favorite shows yet.",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
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
