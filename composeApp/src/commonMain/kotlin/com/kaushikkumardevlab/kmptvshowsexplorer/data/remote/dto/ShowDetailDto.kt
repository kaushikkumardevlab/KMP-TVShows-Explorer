package com.kaushikkumardevlab.kmptvshowsexplorer.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ShowDetailDto(

    val id: Int,
    val name: String,
    val summary: String? = null,
    val genres: List<String>? = null,
    val language: String? = null,
    val rating: RatingDto? = null,
    val image: ImageDto? = null
)
