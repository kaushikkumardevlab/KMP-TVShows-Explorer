package com.kaushikkumardevlab.kmptvshowsexplorer.data.mapper

import com.kaushikkumardevlab.kmptvshowsexplorer.data.remote.dto.ImageDto
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.Image

fun ImageDto.toDomain(): Image {
    return Image(
        medium = medium,
        original = original
    )
}
