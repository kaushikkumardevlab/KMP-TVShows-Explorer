package com.kaushikkumardevlab.kmptvshowsexplorer.core.pagination

data class PagingState<T>(
    val items: List<T> = emptyList(),
    val page: Int = 0,
    val endReached: Boolean = false,
    val isLoading: Boolean = false
)