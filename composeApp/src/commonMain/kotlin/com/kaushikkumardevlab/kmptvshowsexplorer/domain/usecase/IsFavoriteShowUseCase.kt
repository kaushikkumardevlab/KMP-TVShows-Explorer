package com.kaushikkumardevlab.kmptvshowsexplorer.domain.usecase

import com.kaushikkumardevlab.kmptvshowsexplorer.domain.repository.ShowRepository

class IsFavoriteShowUseCase(
    private val repository: ShowRepository
) {
    suspend operator fun invoke(showId: Int): Boolean {
        return repository.isFavorite(showId)
    }
}
