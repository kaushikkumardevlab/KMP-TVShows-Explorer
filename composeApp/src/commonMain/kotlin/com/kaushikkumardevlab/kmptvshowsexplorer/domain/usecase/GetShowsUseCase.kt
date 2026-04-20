package com.kaushikkumardevlab.kmptvshowsexplorer.domain.usecase

import com.kaushikkumardevlab.kmptvshowsexplorer.core.result.Result
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.Show
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.repository.ShowRepository

class GetShowsUseCase(
    private val repository: ShowRepository
) {
    suspend operator fun invoke(page: Int): Result<List<Show>> {
        return repository.getShows(page)
    }
}
