package com.kaushikkumardevlab.kmptvshowsexplorer.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.usecase.GetShowDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.kaushikkumardevlab.kmptvshowsexplorer.core.result.Result
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.Image
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.Rating
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.Show
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.ShowDetail
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.usecase.GetEpisodesUseCase
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.EpisodeGrouper.EpisodeGrouper
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.usecase.IsFavoriteShowUseCase
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.usecase.ToggleFavoriteShowUseCase
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.update

class ShowDetailViewModel(
    private val getShowDetailUseCase: GetShowDetailUseCase,
    private val getEpisodesUseCase: GetEpisodesUseCase,
    private val isFavoriteShowUseCase: IsFavoriteShowUseCase,
    private val toggleFavoriteShowUseCase: ToggleFavoriteShowUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ShowDetailState())
    val state: StateFlow<ShowDetailState> = _state.asStateFlow()

    fun load(showId: Int) {
        Napier.d("Loading show detail for id: $showId")
        _state.update {
            it.copy(
                show = null,
                episodes = emptyList(),
                episodeGroups = emptyList(),
                error = null,
                isLoadingShow = true,
                isLoadingEpisodes = true
            )
        }
        loadShow(showId)
        loadEpisodes(showId)
        loadFavoriteState(showId)
    }

    fun selectSeason(index: Int) {
        _state.update { it.copy(selectedSeasonIndex = index) }
    }

    fun toggleFavorite() {
        val show = _state.value.show ?: return

        viewModelScope.launch {
            val isFavorite = toggleFavoriteShowUseCase(show.toShow())
            _state.update { it.copy(isFavorite = isFavorite) }
        }
    }

    private fun loadShow(showId: Int) {
        viewModelScope.launch {
            when (val result = getShowDetailUseCase(showId)) {
                is Result.Success -> {
                    Napier.d("Successfully loaded show detail: ${result.data.name}")
                    _state.update {
                        it.copy(
                            show = result.data,
                            isLoadingShow = false
                        )
                    }
                }
                is Result.Error -> {
                    Napier.e("Error loading show detail: ${result.message}")
                    _state.update {
                        it.copy(
                            isLoadingShow = false,
                            error = result.message
                        )
                    }
                }
                Result.Loading -> {
                    _state.update { it.copy(isLoadingShow = true) }
                }
            }
        }
    }

    private fun loadEpisodes(showId: Int) {
        viewModelScope.launch {
            when (val result = getEpisodesUseCase(showId)) {
                is Result.Success -> {
                    Napier.d("Successfully loaded ${result.data.size} episodes")
                    _state.update {
                        it.copy(
                            episodes = result.data,
                            episodeGroups = EpisodeGrouper.groupBySeason(result.data),
                            isLoadingEpisodes = false
                        )
                    }
                }
                is Result.Error -> {
                    Napier.e("Error loading episodes: ${result.message}")
                    _state.update {
                        it.copy(
                            isLoadingEpisodes = false
                        )
                    }
                }
                Result.Loading -> {
                    _state.update { it.copy(isLoadingEpisodes = true) }
                }
            }
        }
    }

    private fun loadFavoriteState(showId: Int) {
        viewModelScope.launch {
            _state.update {
                it.copy(isFavorite = isFavoriteShowUseCase(showId))
            }
        }
    }

    private fun ShowDetail.toShow(): Show {
        return Show(
            id = id,
            name = name,
            genres = genres,
            language = language,
            image = Image(
                medium = imageUrl,
                original = imageUrl
            ),
            rating = Rating(
                average = rating
            )
        )
    }

}
