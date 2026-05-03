package com.kaushikkumardevlab.kmptvshowsexplorer.domain.model

data class EpisodeGroup(
    val id: Int,
    val seasonNumber: Int,
    val name: String,
    val episodes: List<Episode>
)

