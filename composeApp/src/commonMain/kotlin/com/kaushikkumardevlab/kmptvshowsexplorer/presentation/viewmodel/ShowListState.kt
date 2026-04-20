package com.kaushikkumardevlab.kmptvshowsexplorer.presentation.viewmodel

import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.Show

data class ShowListState(

    val shows: List<Show> = emptyList(),
    val searchQuery: String = "",
    val selectedCategory: String = "All",
    val categories: List<String> = listOf(
        "All",
        "Drama",
        "Comedy",
        "Action",
        "Science-Fiction"
    ),

    val page: Int = 0,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val endReached: Boolean = false,
    val error: String? = null
)