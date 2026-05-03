package com.kaushikkumardevlab.kmptvshowsexplorer.domain.repository

import com.kaushikkumardevlab.kmptvshowsexplorer.core.result.Result
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.Episode
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.Show
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.ShowDetail

interface ShowRepository {

    suspend fun getShows(page: Int): Result<List<Show>>
    suspend fun searchShows(query: String): Result<List<Show>>
    suspend fun getShowsByCategory(category: String): Result<List<Show>>
    suspend fun getCachedShows(): List<Show>
    suspend fun getShowDetail(id: Int): Result<ShowDetail>
    suspend fun getEpisodes(showId: Int): Result<List<Episode>>
    suspend fun getFavoriteShows(): List<Show>
    suspend fun isFavorite(showId: Int): Boolean
    suspend fun addFavorite(show: Show)
    suspend fun removeFavorite(showId: Int)
}
