package com.kaushikkumardevlab.kmptvshowsexplorer.data.mapper

import com.kaushikkumardevlab.kmptvshowsexplorer.data.local.entity.ShowDetailEntity
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.ShowDetail

fun ShowDetailEntity.toDomain(): ShowDetail {
    return ShowDetail(
        id = id,
        name = name,
        summary = summary,
        genres = genres.split(",").filter { it.isNotBlank() },
        language = language,
        rating = rating,
        imageUrl = imageUrl
    )
}

fun ShowDetail.toEntity(): ShowDetailEntity {
    return ShowDetailEntity(
        id = id,
        name = name,
        summary = summary,
        genres = genres.joinToString(","),
        language = language,
        rating = rating,
        imageUrl = imageUrl
    )
}
