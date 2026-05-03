package com.kaushikkumardevlab.kmptvshowsexplorer.presentation.viewmodel

import com.kaushikkumardevlab.kmptvshowsexplorer.core.result.Result
import com.kaushikkumardevlab.kmptvshowsexplorer.data.repository.MockShowRepository
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.Episode
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.ShowDetail
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.usecase.GetEpisodesUseCase
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.usecase.GetShowDetailUseCase
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.usecase.IsFavoriteShowUseCase
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.usecase.ToggleFavoriteShowUseCase
import com.kaushikkumardevlab.kmptvshowsexplorer.ui.screen.detail.ShowDetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class ShowDetailViewModelTest {

    private lateinit var viewModel: ShowDetailViewModel
    private lateinit var repository: MockShowRepository
    private val testDispatcher = StandardTestDispatcher()

    private val mockShow = ShowDetail(
        id = 1,
        name = "Breaking Bad",
        summary = "Chemistry teacher...",
        genres = listOf("Drama"),
        language = "English",
        rating = 9.5,
        imageUrl = null
    )

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = MockShowRepository()
        
        val getShowDetailUseCase = GetShowDetailUseCase(repository)
        val getEpisodesUseCase = GetEpisodesUseCase(repository)
        val isFavoriteShowUseCase = IsFavoriteShowUseCase(repository)
        val toggleFavoriteShowUseCase = ToggleFavoriteShowUseCase(repository)
        
        viewModel = ShowDetailViewModel(
            getShowDetailUseCase,
            getEpisodesUseCase,
            isFavoriteShowUseCase,
            toggleFavoriteShowUseCase
        )
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `load show details and episodes success`() = runTest {
        repository.detailResult = Result.Success(mockShow)
        repository.episodesResult = Result.Success(listOf(
            Episode(1, "Pilot", 1, 1, "Summary", null)
        ))
        
        viewModel.load(1)
        advanceUntilIdle()
        
        val state = viewModel.state.value
        assertEquals(mockShow, state.show)
        assertEquals(1, state.episodes.size)
        assertEquals(1, state.episodeGroups.size)
        assertEquals(false, state.isLoadingShow)
    }

    @Test
    fun `toggle favorite updates state`() = runTest {
        repository.detailResult = Result.Success(mockShow)
        viewModel.load(1)
        advanceUntilIdle()
        
        assertEquals(false, viewModel.state.value.isFavorite)
        
        viewModel.toggleFavorite()
        advanceUntilIdle()
        
        assertTrue(viewModel.state.value.isFavorite)
        assertEquals(1, repository.addedFavorites.size)
    }
}
