package com.kaushikkumardevlab.kmptvshowsexplorer.data.mapper

import com.kaushikkumardevlab.kmptvshowsexplorer.data.remote.dto.ShowDto
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.Image
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.Rating
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.Show

fun ShowDto.toDomain(): Show {
    return Show(
        id = id,
        name = name,
        genres = genres ?: emptyList(),
        language = language,
        image = image?.toDomain(),
        rating = rating?.toDomain()
    )
}