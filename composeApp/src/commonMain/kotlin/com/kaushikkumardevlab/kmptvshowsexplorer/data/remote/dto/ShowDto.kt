package com.kaushikkumardevlab.kmptvshowsexplorer.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ShowDto(
    val id: Int,
    val name: String,
    val genres: List<String>? = null,
    val language: String? = null,
    val image: ImageDto? = null,
    val rating: RatingDto? = null
)
