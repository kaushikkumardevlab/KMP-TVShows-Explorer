package com.kaushikkumardevlab.kmptvshowsexplorer.data.local.entity

data class ShowDetailEntity(
    val id: Int,
    val name: String,
    val summary: String?,
    val genres: String,
    val language: String?,
    val rating: Double?,
    val imageUrl: String?
)
