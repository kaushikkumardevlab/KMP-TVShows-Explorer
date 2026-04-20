package com.kaushikkumardevlab.kmptvshowsexplorer.domain.repository

import com.kaushikkumardevlab.kmptvshowsexplorer.core.result.Result
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.Show

interface ShowRepository {

    suspend fun getShows(page: Int): Result<List<Show>>

    suspend fun searchShows(query: String): Result<List<Show>>

    suspend fun getShowsByCategory(category: String): Result<List<Show>>

    suspend fun getCachedShows(): List<Show>
}
