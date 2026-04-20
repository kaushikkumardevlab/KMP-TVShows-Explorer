package com.kaushikkumardevlab.kmptvshowsexplorer.domain.usecase

import com.kaushikkumardevlab.kmptvshowsexplorer.core.result.Result
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.Show
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.repository.ShowRepository

class GetShowsByCategoryUseCase(
    private val repository: ShowRepository
) {

    suspend operator fun invoke(category: String): Result<List<Show>> {

        return repository.getShowsByCategory(category)
    }
}