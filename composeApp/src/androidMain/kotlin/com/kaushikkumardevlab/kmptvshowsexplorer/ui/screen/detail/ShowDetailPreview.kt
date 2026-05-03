package com.kaushikkumardevlab.kmptvshowsexplorer.ui.screen.detail

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.Episode
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.ShowDetail

@Preview(showBackground = true)
@Composable
fun EpisodeItemPreview() {
    MaterialTheme {
        EpisodeItem(
            episode = Episode(
                id = 1,
                name = "Pilot",
                season = 1,
                number = 1,
                summary = "A high school chemistry teacher diagnosed with inoperable lung cancer turns to manufacturing and selling methamphetamine in order to secure his family's future.",
                imageUrl = null
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SeasonHeaderPreview() {
    MaterialTheme {
        Surface(
            color = MaterialTheme.colorScheme.secondaryContainer,
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Season 1",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShowDetailContentPreview() {
    MaterialTheme {
        ShowDetailContent(
            state = ShowDetailState(
                show = ShowDetail(
                    id = 1,
                    name = "Breaking Bad",
                    summary = "A high school chemistry teacher diagnosed with inoperable lung cancer turns to manufacturing and selling methamphetamine.",
                    imageUrl = null,
                    rating = 9.5,
                    genres = listOf("Drama", "Crime", "Thriller"),
                    language = "English"
                )
            ),
            onBackClick = {},
            onSeasonSelect = {},
            onToggleFavorite = {},
            onRetry = {}
        )
    }
}
