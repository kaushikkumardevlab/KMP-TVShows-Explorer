package com.kaushikkumardevlab.kmptvshowsexplorer.ui.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Composable
fun ShowDetailScreen(
    showId: Int,
    viewModel: ShowDetailViewModel,
    onBackClick: () -> Unit,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(showId) {
        viewModel.load(showId)
    }

    ShowDetailContent(
        state = state,
        onBackClick = onBackClick,
        onSeasonSelect = { viewModel.selectSeason(it) },
        onToggleFavorite = { viewModel.toggleFavorite() },
        onRetry = { viewModel.load(showId) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowDetailContent(
    state: ShowDetailState,
    onBackClick: () -> Unit,
    onSeasonSelect: (Int) -> Unit,
    onToggleFavorite: () -> Unit,
    onRetry: () -> Unit
) {
    val scrollState = rememberLazyListState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        if (state.isLoadingShow && state.show == null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (state.error != null && state.show == null) {
            Column(
                modifier = Modifier.fillMaxSize().padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = state.error,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Button(onClick = onRetry) {
                    Text("Retry")
                }
            }
        } else {
            LazyColumn(
                state = scrollState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
                item {
                    state.show?.let { show ->
                        Box(modifier = Modifier.fillMaxWidth().height(300.dp)) {
                            AsyncImage(
                                model = show.imageUrl,
                                contentDescription = show.name,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                            
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        brush = Brush.verticalGradient(
                                            0.4f to Color.Black.copy(alpha = 0.3f),
                                            0.7f to Color.Transparent,
                                            1f to MaterialTheme.colorScheme.background
                                        )
                                    )
                            )
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                                .offset(y = (-40).dp)
                        ) {
                            Surface(
                                shape = RoundedCornerShape(16.dp),
                                color = MaterialTheme.colorScheme.surface,
                                tonalElevation = 8.dp,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(20.dp)) {
                                    Text(
                                        text = show.name,
                                        style = MaterialTheme.typography.headlineMedium,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    
                                    Row(
                                        modifier = Modifier.padding(top = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Badge(
                                            containerColor = Color(0xFFFFB400).copy(alpha = 0.1f),
                                            contentColor = Color(0xFFE5A100)
                                        ) {
                                            Row(
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Icon(Icons.Default.Star, null, modifier = Modifier.size(14.dp))
                                                Spacer(Modifier.width(4.dp))
                                                Text(
                                                    text = show.rating?.toString() ?: "0.0",
                                                    fontWeight = FontWeight.Bold
                                                )
                                            }
                                        }
                                        
                                        Spacer(Modifier.width(12.dp))
                                        
                                        Text(
                                            text = show.genres.joinToString(" • "),
                                            style = MaterialTheme.typography.labelMedium,
                                            color = MaterialTheme.colorScheme.primary,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
                            }

                            Spacer(Modifier.height(12.dp))

                            Text(
                                text = "Overview",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )

                            Text(
                                text = show.summary ?: "",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                lineHeight = 22.sp
                            )
                        }
                    }
                }

                if (state.episodeGroups.isNotEmpty()) {
                    item {
                        Text(
                            text = "Episodes",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 24.dp, top = 0.dp, bottom = 8.dp)
                        )
                    }

                    // Season Selector
                    item {
                        ScrollableTabRow(
                            selectedTabIndex = state.selectedSeasonIndex,
                            edgePadding = 24.dp,
                            containerColor = Color.Transparent,
                            divider = {},
                            indicator = { tabPositions ->
                                if (state.selectedSeasonIndex < tabPositions.size) {
                                    TabRowDefaults.SecondaryIndicator(
                                        modifier = Modifier.tabIndicatorOffset(tabPositions[state.selectedSeasonIndex]),
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            },
                            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                        ) {
                            state.episodeGroups.forEachIndexed { index, group ->
                                Tab(
                                    selected = state.selectedSeasonIndex == index,
                                    onClick = { onSeasonSelect(index) },
                                    text = {
                                        Text(
                                            text = group.name,
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = if (state.selectedSeasonIndex == index) FontWeight.Bold else FontWeight.Normal
                                        )
                                    },
                                    selectedContentColor = MaterialTheme.colorScheme.primary,
                                    unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    // Selected Season Episodes
                    val selectedGroup = state.episodeGroups.getOrNull(state.selectedSeasonIndex)
                    if (selectedGroup != null) {
                        items(selectedGroup.episodes, key = { it.id }) { episode ->
                            EpisodeItem(episode)
                        }
                    }
                } else if (!state.isLoadingEpisodes && state.show != null) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No episodes found",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                if (state.isLoadingEpisodes) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(strokeWidth = 3.dp)
                        }
                    }
                }
            }
        }

        // Floating Navigation
        val isScrolled = remember { derivedStateOf { scrollState.firstVisibleItemIndex > 0 || scrollState.firstVisibleItemScrollOffset > 200 } }
        
        Surface(
            modifier = Modifier.fillMaxWidth().statusBarsPadding(),
            color = if (isScrolled.value) MaterialTheme.colorScheme.surface.copy(alpha = 0.95f) else Color.Transparent,
            tonalElevation = if (isScrolled.value) 4.dp else 0.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(if (isScrolled.value) Color.Transparent else Color.Black.copy(alpha = 0.3f))
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = if (isScrolled.value) MaterialTheme.colorScheme.onSurface else Color.White
                    )
                }
                
                if (isScrolled.value) {
                    Text(
                        text = state.show?.name ?: "",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f).padding(horizontal = 16.dp),
                        maxLines = 1
                    )
                } else {
                    Spacer(Modifier.weight(1f))
                }

                state.show?.let {
                    IconButton(
                        onClick = onToggleFavorite,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(if (isScrolled.value) Color.Transparent else Color.Black.copy(alpha = 0.3f))
                    ) {
                        Icon(
                            imageVector = if (state.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = null,
                            tint = if (state.isFavorite) Color.Red else (if (isScrolled.value) MaterialTheme.colorScheme.onSurface else Color.White),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}
