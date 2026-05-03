package com.kaushikkumardevlab.kmptvshowsexplorer.data.mapper

import com.kaushikkumardevlab.kmptvshowsexplorer.core.text.HtmlTextFormatter
import com.kaushikkumardevlab.kmptvshowsexplorer.data.remote.dto.EpisodeDto
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.Episode

fun EpisodeDto.toDomain(): Episode {

    return Episode(
        id = id,
        name = name,
        season = season,
        number = number ?: 0,
        summary = HtmlTextFormatter.toPlainText(summary),
        imageUrl = image?.medium
    )
}
fun List<EpisodeDto>.toDomainList(): List<Episode> {
    return map { it.toDomain() }
}
