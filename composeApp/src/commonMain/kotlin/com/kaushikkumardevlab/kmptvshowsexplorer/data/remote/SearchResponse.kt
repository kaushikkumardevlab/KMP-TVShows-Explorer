package com.kaushikkumardevlab.kmptvshowsexplorer.data.remote

import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.Show
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    val show: Show
)