package com.kaushikkumardevlab.kmptvshowsexplorer.data.local.entity

data class ShowEntity(
    val id: Int,
    val name: String,
    val genres: String,
    val language: String?,
    val imageMedium: String?,
    val imageOriginal: String?,
    val rating: Double?
)