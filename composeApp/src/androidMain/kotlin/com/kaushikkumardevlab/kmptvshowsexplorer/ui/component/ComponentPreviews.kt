package com.kaushikkumardevlab.kmptvshowsexplorer.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.Rating
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.Show

@Preview(showBackground = true)
@Composable
fun ShowItemPreview() {
    MaterialTheme {
        ShowItem(
            show = Show(
                id = 1,
                name = "Breaking Bad",
                genres = listOf("Drama", "Crime", "Thriller"),
                language = "English",
                rating = Rating(average = 9.5)
            ),
            onClick = {}
        )
    }
}
