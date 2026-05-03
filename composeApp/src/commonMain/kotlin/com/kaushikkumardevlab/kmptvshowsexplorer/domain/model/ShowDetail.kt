package com.kaushikkumardevlab.kmptvshowsexplorer.domain.model

data class ShowDetail(
    val id: Int,
    val name: String,
    val summary: String?,
    val genres: List<String>,
    val language: String?,
    val rating: Double?,
    val imageUrl: String?
)