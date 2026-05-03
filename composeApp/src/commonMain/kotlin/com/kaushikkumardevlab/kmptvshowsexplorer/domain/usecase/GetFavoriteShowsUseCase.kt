package com.kaushikkumardevlab.kmptvshowsexplorer.domain.usecase

import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.Show
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.repository.ShowRepository

class GetFavoriteShowsUseCase(
    private val repository: ShowRepository
) {
    suspend operator fun invoke(): List<Show> {
        return repository.getFavoriteShows()
    }
}
