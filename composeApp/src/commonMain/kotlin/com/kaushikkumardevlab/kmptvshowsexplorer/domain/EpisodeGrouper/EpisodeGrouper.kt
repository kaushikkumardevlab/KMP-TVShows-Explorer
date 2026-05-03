package com.kaushikkumardevlab.kmptvshowsexplorer.domain.EpisodeGrouper

import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.Episode
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.EpisodeGroup

object EpisodeGrouper {

    fun groupBySeason(
        episodes: List<Episode>
    ): List<EpisodeGroup> {

        return episodes
            .groupBy { it.season }
            .map { (season, episodes) ->
                EpisodeGroup(
                    id = season,
                    seasonNumber = season,
                    name = "Season $season",
                    episodes = episodes.sortedBy { it.number }
                )
            }
            .sortedBy { it.seasonNumber }
    }
}