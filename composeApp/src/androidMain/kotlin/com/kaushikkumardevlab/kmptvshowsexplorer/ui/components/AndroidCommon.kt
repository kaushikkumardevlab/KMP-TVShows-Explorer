package com.kaushikkumardevlab.kmptvshowsexplorer.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaushikkumardevlab.kmptvshowsexplorer.presentation.viewmodel.ShowListViewModel
import com.kaushikkumardevlab.kmptvshowsexplorer.ui.component.ShowItem
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.Show
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.Image
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.Rating

@Composable
fun AndroidShowList(
    viewModel: ShowListViewModel,
    onShowClick: (Int) -> Unit
) {
    val state by viewModel.state.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(state.shows) { index, show ->
            if (
                state.searchQuery.isBlank() &&
                state.selectedCategory == "All" &&
                index >= state.shows.size - 1 &&
                !state.endReached &&
                !state.isLoading
            ) {
                viewModel.loadNextPage()
            }

            ShowItem(
                show = show,
                onClick = { onShowClick(show.id) }
            )
        }
    }
}

@Composable
fun LoadingAndroidState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun EmptyAndroidState(message: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoadingAndroidState() {
    MaterialTheme {
        LoadingAndroidState()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEmptyAndroidState() {
    MaterialTheme {
        EmptyAndroidState(message = "No shows found.")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewShowItem() {
    MaterialTheme {
        Box(modifier = Modifier.padding(8.dp)) {
            ShowItem(
                show = Show(
                    id = 1,
                    name = "Example Show",
                    image = Image(medium = "https://example.com/image.jpg"),
                    rating = Rating(average = 8.5),
                    genres = listOf("Drama", "Comedy")
                ),
                onClick = {}
            )
        }
    }
}
