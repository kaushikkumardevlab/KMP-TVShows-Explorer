package com.kaushikkumardevlab.kmptvshowsexplorer.data.remote

import com.kaushikkumardevlab.kmptvshowsexplorer.core.network.NetworkConstants
import com.kaushikkumardevlab.kmptvshowsexplorer.data.remote.dto.SearchResponseDto
import com.kaushikkumardevlab.kmptvshowsexplorer.data.remote.dto.ShowDto
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.Show
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class ShowApi(
    private val client: HttpClient
) {

    suspend fun getShows(page: Int): List<ShowDto> {

        return client.get(NetworkConstants.SHOWS) {
            parameter("page", page)
        }.body()
    }

    suspend fun searchShows(query: String): List<SearchResponseDto> {
        return client.get(NetworkConstants.SEARCH_SHOWS) {
            parameter("q", query)
        }.body()
        //"https://api.tvmaze.com/search/shows?q=$query"
    }
}