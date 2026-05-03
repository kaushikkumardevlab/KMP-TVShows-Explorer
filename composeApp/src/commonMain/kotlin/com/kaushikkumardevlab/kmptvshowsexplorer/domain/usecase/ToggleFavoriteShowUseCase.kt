package com.kaushikkumardevlab.kmptvshowsexplorer.domain.usecase

import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.Show
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.repository.ShowRepository

class ToggleFavoriteShowUseCase(
    private val repository: ShowRepository
) {
    suspend operator fun invoke(show: Show): Boolean {
        val isFavorite = repository.isFavorite(show.id)

        if (isFavorite) {
            repository.removeFavorite(show.id)
            return false
        }

        repository.addFavorite(show)
        return true
    }
}
