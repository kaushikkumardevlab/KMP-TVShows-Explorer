package com.kaushikkumardevlab.kmptvshowsexplorer.domain.usecase

import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.ShowDetail
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.repository.ShowRepository
import com.kaushikkumardevlab.kmptvshowsexplorer.core.result.Result

class GetShowDetailUseCase(private val repository: ShowRepository) {
    suspend operator fun invoke(id: Int): Result<ShowDetail> {
        return repository.getShowDetail(id)
    }
}