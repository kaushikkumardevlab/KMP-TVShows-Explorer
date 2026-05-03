package com.kaushikkumardevlab.kmptvshowsexplorer.ui.screen.detail

import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.Episode
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.EpisodeGroup
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.ShowDetail

data class ShowDetailState(
    val show: ShowDetail? = null,
    val episodes: List<Episode> = emptyList(),
    val episodeGroups: List<EpisodeGroup> = emptyList(),
    val selectedSeasonIndex: Int = 0,
    val isFavorite: Boolean = false,
    val isLoadingShow: Boolean = false,
    val isLoadingEpisodes: Boolean = false,
    val error: String? = null
)
