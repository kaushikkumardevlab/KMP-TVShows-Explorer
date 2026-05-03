package com.kaushikkumardevlab.kmptvshowsexplorer.data.remote.datasource

import com.kaushikkumardevlab.kmptvshowsexplorer.data.remote.ShowApi
import com.kaushikkumardevlab.kmptvshowsexplorer.data.remote.dto.EpisodeDto
import com.kaushikkumardevlab.kmptvshowsexplorer.data.remote.dto.SearchResponseDto
import com.kaushikkumardevlab.kmptvshowsexplorer.data.remote.dto.ShowDetailDto
import com.kaushikkumardevlab.kmptvshowsexplorer.data.remote.dto.ShowDto

class ShowRemoteDataSource(
    private val api: ShowApi
) {

    suspend fun getShows(page: Int): List<ShowDto> {
        return api.getShows(page)
    }

    suspend fun searchShows(query: String): List<SearchResponseDto> {
        return api.searchShows(query)
    }

    suspend fun getShowDetail(id: Int): ShowDetailDto {
        return api.getShowDetail(id)
    }
    suspend fun getEpisodes(showId: Int): List<EpisodeDto> {
        return api.getEpisodes(showId)
    }

}
