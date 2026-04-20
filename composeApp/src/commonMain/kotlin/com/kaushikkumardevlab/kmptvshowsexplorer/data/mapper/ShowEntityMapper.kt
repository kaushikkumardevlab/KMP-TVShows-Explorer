package com.kaushikkumardevlab.kmptvshowsexplorer.data.mapper

import com.kaushikkumardevlab.kmptvshowsexplorer.data.local.entity.ShowEntity
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.Image
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.Rating
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.Show

fun ShowEntity.toDomain(): Show {
    return Show(
        id = id,
        name = name,
        genres = genres.split(",").filter { it.isNotBlank() },
        language = language,
        image = Image(
            medium = imageMedium,
            original = imageOriginal
        ),
        rating = Rating(
            average = rating
        )
    )
}

fun Show.toEntity(): ShowEntity {
    return ShowEntity(
        id = id,
        name = name,
        genres = genres.joinToString(","),
        language = language,
        imageMedium = image?.medium,
        imageOriginal = image?.original,
        rating = rating?.average
    )
}
