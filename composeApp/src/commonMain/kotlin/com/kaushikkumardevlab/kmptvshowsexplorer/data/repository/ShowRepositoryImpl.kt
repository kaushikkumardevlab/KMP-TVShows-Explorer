package com.kaushikkumardevlab.kmptvshowsexplorer.data.repository

import com.kaushikkumardevlab.kmptvshowsexplorer.core.result.Result
import com.kaushikkumardevlab.kmptvshowsexplorer.core.result.safeApiCall
import com.kaushikkumardevlab.kmptvshowsexplorer.data.local.datasource.ShowLocalDataSource
import com.kaushikkumardevlab.kmptvshowsexplorer.data.remote.datasource.ShowRemoteDataSource
import com.kaushikkumardevlab.kmptvshowsexplorer.data.mapper.toDomain
import com.kaushikkumardevlab.kmptvshowsexplorer.data.mapper.toEntity
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.Show
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.repository.ShowRepository

class ShowRepositoryImpl(
    private val remote: ShowRemoteDataSource,
    private val local: ShowLocalDataSource
) : ShowRepository {

    override suspend fun getShows(page: Int): Result<List<Show>> {
        return safeApiCall {
            val remoteShows = remote.getShows(page)
            val domainShows = remoteShows.map { it.toDomain() }

            if (page == 0) {
                local.clearShows()
            }
            local.insertShows(domainShows.map { it.toEntity() })

            domainShows
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

    override suspend fun getShowsByCategory(category: String): Result<List<Show>> {
        return safeApiCall {
            // For categories, we search all (page 0) and filter
            val shows = remote.getShows(0)
            shows.map { it.toDomain() }
                .filter { it.genres.contains(category) }
        }
    }
}
