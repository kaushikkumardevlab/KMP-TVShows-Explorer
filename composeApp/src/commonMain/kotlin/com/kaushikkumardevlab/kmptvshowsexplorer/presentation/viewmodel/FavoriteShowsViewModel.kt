package com.kaushikkumardevlab.kmptvshowsexplorer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.usecase.GetFavoriteShowsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoriteShowsViewModel(
    private val getFavoriteShowsUseCase: GetFavoriteShowsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(FavoriteShowsState())
    val state: StateFlow<FavoriteShowsState> = _state.asStateFlow()

    fun loadFavorites() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            _state.update {
                it.copy(
                    shows = getFavoriteShowsUseCase(),
                    isLoading = false
                )
            }
        }
    }
}
