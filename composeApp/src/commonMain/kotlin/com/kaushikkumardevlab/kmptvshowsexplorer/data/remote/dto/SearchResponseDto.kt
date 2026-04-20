package com.kaushikkumardevlab.kmptvshowsexplorer.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class SearchResponseDto(
    val score: Double,
    val show: ShowDto
)