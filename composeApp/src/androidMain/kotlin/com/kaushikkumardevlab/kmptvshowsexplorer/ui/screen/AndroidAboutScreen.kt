package com.kaushikkumardevlab.kmptvshowsexplorer.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AndroidAboutScreen() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Project showcase") })
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "KMP TV Shows Explorer",
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Android has an expanded navigation surface, while iOS keeps the compact shared flow.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            item {
                ShowcaseRow("Shared", "Ktor, SQLDelight, Koin, repositories, use cases, and view models")
            }

            item {
                ShowcaseRow("Android", "Bottom navigation with list, search, category browsing, saved shows, detail, and project info")
            }

            item {
                ShowcaseRow("iOS", "Simple shared Compose flow hosted from SwiftUI")
            }
        }
    }
}

@Composable
private fun ShowcaseRow(
    title: String,
    description: String
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
