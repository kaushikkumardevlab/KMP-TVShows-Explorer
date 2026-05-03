package com.kaushikkumardevlab.kmptvshowsexplorer.data.repository

import com.kaushikkumardevlab.kmptvshowsexplorer.core.result.Result
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.Episode
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.Show
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.ShowDetail
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.repository.ShowRepository

class MockShowRepository : ShowRepository {
    var showsResult: Result<List<Show>> = Result.Success(emptyList())
    var searchResult: Result<List<Show>> = Result.Success(emptyList())
    var detailResult: Result<ShowDetail> = Result.Error("Not implemented")
    var episodesResult: Result<List<Episode>> = Result.Success(emptyList())
    
    var addedFavorites = mutableListOf<Show>()

    override suspend fun getShows(page: Int): Result<List<Show>> = showsResult
    
    override suspend fun searchShows(query: String): Result<List<Show>> = searchResult
    
    override suspend fun getShowsByCategory(category: String): Result<List<Show>> = showsResult
    
    override suspend fun getCachedShows(): List<Show> = (showsResult as? Result.Success)?.data ?: emptyList()
    
    override suspend fun getShowDetail(id: Int): Result<ShowDetail> = detailResult
    
    override suspend fun getEpisodes(showId: Int): Result<List<Episode>> = episodesResult
    
    override suspend fun getFavoriteShows(): List<Show> = addedFavorites
    
    override suspend fun isFavorite(showId: Int): Boolean = addedFavorites.any { it.id == showId }
    
    override suspend fun addFavorite(show: Show) {
        addedFavorites.add(show)
    }
    
    override suspend fun removeFavorite(showId: Int) {
        addedFavorites.removeAll { it.id == showId }
    }
}
