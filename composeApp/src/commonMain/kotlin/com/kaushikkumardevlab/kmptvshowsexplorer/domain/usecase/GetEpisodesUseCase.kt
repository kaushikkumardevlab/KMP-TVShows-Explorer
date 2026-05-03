package com.kaushikkumardevlab.kmptvshowsexplorer.domain.usecase

import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.Episode
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.repository.ShowRepository
import com.kaushikkumardevlab.kmptvshowsexplorer.core.result.Result

class GetEpisodesUseCase(
    private val repository: ShowRepository
) {
    suspend operator fun invoke(showId: Int): Result<List<Episode>> {
        return repository.getEpisodes(showId)
    }
}