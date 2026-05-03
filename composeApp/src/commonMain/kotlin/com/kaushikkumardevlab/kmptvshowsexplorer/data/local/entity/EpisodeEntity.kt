package com.kaushikkumardevlab.kmptvshowsexplorer.data.local.entity

data class EpisodeEntity(
    val id: Int,
    val showId: Int,
    val name: String,
    val season: Int,
    val number: Int,
    val summary: String?,
    val imageUrl: String?
)
