package com.kaushikkumardevlab.kmptvshowsexplorer.data.mapper

import com.kaushikkumardevlab.kmptvshowsexplorer.core.text.HtmlTextFormatter
import com.kaushikkumardevlab.kmptvshowsexplorer.data.remote.dto.ShowDetailDto
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.ShowDetail

fun ShowDetailDto.toDomain(): ShowDetail {

    return ShowDetail(
        id = id,
        name = name,
        summary = HtmlTextFormatter.toPlainText(summary),
        genres = genres ?: emptyList(),
        language = language,
        rating = rating?.average,
        imageUrl = image?.original
    )
}

fun List<ShowDetailDto>?.toDomainList(): List<ShowDetail> {
    return this?.map { it.toDomain() } ?: emptyList()
}
