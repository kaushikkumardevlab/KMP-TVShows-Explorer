package com.kaushikkumardevlab.kmptvshowsexplorer.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class EpisodeDto(
    val id: Int,
    val name: String,
    val season: Int,
    val number: Int? = null,
    val summary: String? = null,
    val image: ImageDto? = null
)
