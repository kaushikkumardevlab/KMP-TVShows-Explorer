package com.kaushikkumardevlab.kmptvshowsexplorer.data.mapper

import com.kaushikkumardevlab.kmptvshowsexplorer.data.local.entity.EpisodeEntity
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.Episode

fun EpisodeEntity.toDomain(): Episode {
    return Episode(
        id = id,
        name = name,
        season = season,
        number = number,
        summary = summary,
        imageUrl = imageUrl
    )
}

fun Episode.toEntity(showId: Int): EpisodeEntity {
    return EpisodeEntity(
        id = id,
        showId = showId,
        name = name,
        season = season,
        number = number,
        summary = summary,
        imageUrl = imageUrl
    )
}
