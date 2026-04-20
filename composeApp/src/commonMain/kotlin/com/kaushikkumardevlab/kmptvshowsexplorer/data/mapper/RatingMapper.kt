package com.kaushikkumardevlab.kmptvshowsexplorer.data.mapper

import com.kaushikkumardevlab.kmptvshowsexplorer.data.remote.dto.RatingDto
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.Rating

fun RatingDto.toDomain(): Rating {
    return Rating(
        average = average
    )
}
