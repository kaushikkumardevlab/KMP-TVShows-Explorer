package com.kaushikkumardevlab.kmptvshowsexplorer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaushikkumardevlab.kmptvshowsexplorer.core.pagination.DefaultPaginator
import com.kaushikkumardevlab.kmptvshowsexplorer.core.result.Result
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.usecase.GetShowsByCategoryUseCase
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.usecase.GetShowsUseCase
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.usecase.SearchShowsUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ShowListViewModel(
    private val getShowsUseCase: GetShowsUseCase,
    private val searchShowsUseCase: SearchShowsUseCase,
    private val getShowsByCategoryUseCase: GetShowsByCategoryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ShowListState())
    val state: StateFlow<ShowListState> = _state.asStateFlow()

    private val searchQuery = MutableStateFlow("")

    private val paginator = DefaultPaginator(
        initialKey = 0,
        onLoadUpdated = { loading ->
            _state.update { it.copy(isLoading = loading) }
        },
        onRequest = { nextKey ->
            getShowsUseCase(nextKey)
        },
        getNextKey = { items ->
            _state.value.page + 1
        },
        onError = { message ->
            _state.update { it.copy(errorMessage = message, isLoading = false) }
        },
        onSuccess = { items, newKey ->
            _state.update {
                it.copy(
                    shows = (it.shows + items).distinctBy { show -> show.id },
                    page = newKey,
                    isLoading = false,
                    errorMessage = null,
                    endReached = items.isEmpty()
                )
            }
        }
    )

    init {
        observeSearch()
        loadNextPage()
    }

    /**
     * Pagination loader
     */
    fun loadNextPage() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    /**
     * Search input from UI
     */
    fun onSearchQueryChange(query: String) {
        searchQuery.value = query
        _state.update { it.copy(searchQuery = query) }
    }

    /**
     * Observe search with debounce
     */
    @OptIn(FlowPreview::class)
    private fun observeSearch() {
        viewModelScope.launch {
            searchQuery
                .debounce(500)
                .distinctUntilChanged()
                .collect { query ->
                    if (query.isBlank()) {
                        resetPagination()
                        loadNextPage()
                    } else {
                        performSearch(query)
                    }
                }
        }
    }

    /**
     * Perform API search
     */
    private suspend fun performSearch(query: String) {
        _state.update { it.copy(isLoading = true, shows = emptyList()) }

        when (val result = searchShowsUseCase(query)) {
            is Result.Success -> {
                _state.update {
                    it.copy(
                        shows = result.data,
                        isLoading = false,
                        errorMessage = null
                    )
                }
            }
            is Result.Error -> {
                _state.update {
                    it.copy(
                        errorMessage = result.message,
                        isLoading = false
                    )
                }
            }
            else -> {}
        }
    }

    /**
     * Category filter
     */
    fun selectCategory(category: String) {
        viewModelScope.launch {
            if (category == "All") {
                _state.update { it.copy(selectedCategory = category) }
                resetPagination()
                loadNextPage()
                return@launch
            }

            _state.update { it.copy(isLoading = true, shows = emptyList()) }

            when (val result = getShowsByCategoryUseCase(category)) {
                is Result.Success -> {
                    _state.update {
                        it.copy(
                            selectedCategory = category,
                            shows = result.data,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
                is Result.Error -> {
                    _state.update {
                        it.copy(
                            errorMessage = result.message,
                            isLoading = false
                        )
                    }
                }
                else -> {}
            }
        }
    }

    /**
     * Reset pagination when needed
     */
    private fun resetPagination() {
        paginator.reset()
        _state.update {
            it.copy(
                shows = emptyList(),
                errorMessage = null,
                endReached = false
            )
        }
    }
}
