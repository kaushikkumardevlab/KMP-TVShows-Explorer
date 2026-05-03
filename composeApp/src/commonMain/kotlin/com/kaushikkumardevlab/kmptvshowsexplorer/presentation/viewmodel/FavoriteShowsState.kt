package com.kaushikkumardevlab.kmptvshowsexplorer.presentation.viewmodel

import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.Show

data class FavoriteShowsState(
    val shows: List<Show> = emptyList(),
    val isLoading: Boolean = false
) {
    val isEmpty: Boolean
        get() = shows.isEmpty() && !isLoading
}
