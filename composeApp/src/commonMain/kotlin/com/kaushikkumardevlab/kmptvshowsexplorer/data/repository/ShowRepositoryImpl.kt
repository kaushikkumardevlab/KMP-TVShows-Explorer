package com.kaushikkumardevlab.kmptvshowsexplorer.data.repository

import com.kaushikkumardevlab.kmptvshowsexplorer.core.network.retry.retryWithBackoff
import com.kaushikkumardevlab.kmptvshowsexplorer.core.result.Result
import com.kaushikkumardevlab.kmptvshowsexplorer.core.result.safeApiCall
import com.kaushikkumardevlab.kmptvshowsexplorer.data.local.datasource.ShowLocalDataSource
import com.kaushikkumardevlab.kmptvshowsexplorer.data.remote.datasource.ShowRemoteDataSource
import com.kaushikkumardevlab.kmptvshowsexplorer.data.mapper.toDomain
import com.kaushikkumardevlab.kmptvshowsexplorer.data.mapper.toEntity
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.Episode
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.Show
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.ShowDetail
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.repository.ShowRepository

class ShowRepositoryImpl(
    private val remote: ShowRemoteDataSource,
    private val local: ShowLocalDataSource
) : ShowRepository {

    override suspend fun getShows(page: Int): Result<List<Show>> {
        val remoteResult = safeApiCall {
            retryWithBackoff {
                val remoteShows = remote.getShows(page)
                val domainShows = remoteShows.map { it.toDomain() }

                if (page == 0) {
                    local.clearShows()
                }
                local.insertShows(domainShows.map { it.toEntity() })

                domainShows
            }
        }

        return if (remoteResult is Result.Error && page == 0) {
            val cached = local.getShows().map { it.toDomain() }
            if (cached.isNotEmpty()) Result.Success(cached) else remoteResult
        } else {
            remoteResult
        }
    }

    override suspend fun searchShows(query: String): Result<List<Show>> {
        return safeApiCall {
            val searchResponse = remote.searchShows(query)
            searchResponse.map { it.show.toDomain() }
        }
    }

    override suspend fun getCachedShows(): List<Show> {
        return local.getShows().map { it.toDomain() }
    }

    override suspend fun getFavoriteShows(): List<Show> {
        return local.getFavoriteShows().map { it.toDomain() }
    }

    override suspend fun isFavorite(showId: Int): Boolean {
        return local.isFavorite(showId)
    }

    override suspend fun addFavorite(show: Show) {
        local.addFavorite(show.toEntity())
    }

    override suspend fun removeFavorite(showId: Int) {
        local.removeFavorite(showId)
    }

    override suspend fun getShowsByCategory(category: String): Result<List<Show>> {
        return safeApiCall {
            // For categories, we search all (page 0) and filter
            val shows = remote.getShows(0)
            shows.map { it.toDomain() }
                .filter { it.genres.contains(category) }
        }
    }

    override suspend fun getShowDetail(id: Int): Result<ShowDetail> {
        val remoteResult = safeApiCall {
            val dto = remote.getShowDetail(id)
            val domain = dto.toDomain()
            local.insertShowDetail(domain.toEntity())
            domain
        }

        return if (remoteResult is Result.Error) {
            val cached = local.getShowDetail(id)?.toDomain()
            if (cached != null) Result.Success(cached) else remoteResult
        } else {
            remoteResult
        }
    }

    override suspend fun getEpisodes(showId: Int): Result<List<Episode>> {
        val remoteResult = safeApiCall {
            val episodes = remote.getEpisodes(showId)
            val domain = episodes.map { it.toDomain() }
            local.insertEpisodes(domain.map { it.toEntity(showId) })
            domain
        }

        return if (remoteResult is Result.Error) {
            val cached = local.getEpisodes(showId).map { it.toDomain() }
            if (cached.isNotEmpty()) Result.Success(cached) else remoteResult
        } else {
            remoteResult
        }
    }
}
