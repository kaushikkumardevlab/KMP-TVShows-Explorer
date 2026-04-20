package com.kaushikkumardevlab.kmptvshowsexplorer.domain.model


import kotlinx.serialization.Serializable


@Serializable
data class Show(
    val id: Int,
    val name: String,
    val genres: List<String> = emptyList(),
    val language: String? = null,
    val image: Image? = null,
    val rating: Rating? = null
)

@Serializable
data class Image(
    val medium: String? = null,
    val original: String? = null
)

@Serializable
data class Rating(
    val average: Double? = null
)