package com.kaushikkumardevlab.kmptvshowsexplorer.presentation.viewmodel

import com.kaushikkumardevlab.kmptvshowsexplorer.core.result.Result
import com.kaushikkumardevlab.kmptvshowsexplorer.data.repository.MockShowRepository
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.Show
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.usecase.GetShowsByCategoryUseCase
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.usecase.GetShowsUseCase
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.usecase.SearchShowsUseCase
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
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class ShowListViewModelTest {

    private lateinit var viewModel: ShowListViewModel
    private lateinit var repository: MockShowRepository
    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = MockShowRepository()
        
        val getShowsUseCase = GetShowsUseCase(repository)
        val searchShowsUseCase = SearchShowsUseCase(repository)
        val getShowsByCategoryUseCase = GetShowsByCategoryUseCase(repository)
        
        viewModel = ShowListViewModel(
            getShowsUseCase,
            searchShowsUseCase,
            getShowsByCategoryUseCase
        )
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is loading then success`() = runTest {
        val shows = listOf(Show(id = 1, name = "Test Show"))
        repository.showsResult = Result.Success(shows)
        
        // Re-init to trigger init block with mock data if needed, 
        // or just let the first load finish
        viewModel.loadNextPage()
        advanceUntilIdle()
        
        val state = viewModel.state.value
        assertEquals(shows, state.shows)
        assertEquals(false, state.isLoading)
    }

    @Test
    fun `search query updates state and performs search`() = runTest {
        val searchResults = listOf(Show(id = 2, name = "Search Result"))
        repository.searchResult = Result.Success(searchResults)
        
        viewModel.onSearchQueryChange("Breaking")
        
        // Advance time for debounce (500ms)
        testDispatcher.scheduler.advanceTimeBy(600)
        advanceUntilIdle()
        
        val state = viewModel.state.value
        assertEquals("Breaking", state.searchQuery)
        assertEquals(searchResults, state.shows)
    }

    @Test
    fun `select category updates state and filters`() = runTest {
        val categoryShows = listOf(Show(id = 3, name = "Drama Show", genres = listOf("Drama")))
        repository.showsResult = Result.Success(categoryShows)
        
        viewModel.selectCategory("Drama")
        advanceUntilIdle()
        
        val state = viewModel.state.value
        assertEquals("Drama", state.selectedCategory)
        assertEquals(categoryShows, state.shows)
    }

    @Test
    fun `loadNextPage increments page number`() = runTest {
        repository.showsResult = Result.Success(listOf(Show(id = 1, name = "Page 0")))
        
        // First page load (triggered by init or manual)
        advanceUntilIdle()
        assertEquals(1, viewModel.state.value.page)
        
        repository.showsResult = Result.Success(listOf(Show(id = 2, name = "Page 1")))
        viewModel.loadNextPage()
        advanceUntilIdle()
        
        assertEquals(2, viewModel.state.value.page)
        assertEquals(2, viewModel.state.value.shows.size)
    }
}
