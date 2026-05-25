package com.kaushikkumardevlab.kmptvshowsexplorer

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.window.ComposeUIViewController
import com.kaushikkumardevlab.kmptvshowsexplorer.data.local.database.DatabaseDriverFactory
import com.kaushikkumardevlab.kmptvshowsexplorer.di.appModule
import com.kaushikkumardevlab.kmptvshowsexplorer.presentation.viewmodel.FavoriteShowsViewModel
import com.kaushikkumardevlab.kmptvshowsexplorer.presentation.viewmodel.ShowListViewModel
import com.kaushikkumardevlab.kmptvshowsexplorer.presentation.viewmodel.ThemeViewModel
import com.kaushikkumardevlab.kmptvshowsexplorer.core.result.Result
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.Image
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.Rating
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.Show
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.ShowDetail
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.usecase.GetEpisodesUseCase
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.usecase.GetFavoriteShowsUseCase
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.usecase.GetShowDetailUseCase
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.usecase.GetShowsByCategoryUseCase
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.usecase.GetShowsUseCase
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.usecase.IsFavoriteShowUseCase
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.usecase.SearchShowsUseCase
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.usecase.ToggleFavoriteShowUseCase
import com.kaushikkumardevlab.kmptvshowsexplorer.ui.screen.*
import com.kaushikkumardevlab.kmptvshowsexplorer.ui.screen.detail.ShowDetailScreen
import com.kaushikkumardevlab.kmptvshowsexplorer.ui.screen.detail.ShowDetailViewModel
import com.kaushikkumardevlab.kmptvshowsexplorer.ui.theme.AppTheme
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import platform.UIKit.UIViewController
import kotlin.native.ObjCName
import kotlin.experimental.ExperimentalObjCName
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.annotation.KoinExperimentalAPI
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import com.kaushikkumardevlab.kmptvshowsexplorer.ui.theme.ThemeSettings
import kotlinx.coroutines.flow.collect

@OptIn(ExperimentalObjCName::class)
@ObjCName("KoinHelper")
class KoinHelper : KoinComponent {
    private val scope = MainScope()

    fun start() {
        startKoin {
            modules(
                appModule() + module {
                    single { DatabaseDriverFactory() }
                }
            )
        }
    }

    fun observeTheme(onThemeChanged: (Boolean?) -> Unit) {
        scope.launch {
            ThemeSettings.isDarkTheme.collect { isDark ->
                onThemeChanged(isDark)
            }
        }
    }

    fun loadShows(
        page: Int,
        onSuccess: (List<Show>) -> Unit,
        onError: (String) -> Unit
    ) {
        scope.launch {
            when (val result = get<GetShowsUseCase>()(page)) {
                is Result.Success -> onSuccess(result.data)
                is Result.Error -> onError(result.message)
                Result.Loading -> Unit
            }
        }
    }

    fun searchShows(
        query: String,
        onSuccess: (List<Show>) -> Unit,
        onError: (String) -> Unit
    ) {
        scope.launch {
            when (val result = get<SearchShowsUseCase>()(query)) {
                is Result.Success -> onSuccess(result.data)
                is Result.Error -> onError(result.message)
                Result.Loading -> Unit
            }
        }
    }

    fun loadShowsByCategory(
        category: String,
        onSuccess: (List<Show>) -> Unit,
        onError: (String) -> Unit
    ) {
        scope.launch {
            when (val result = get<GetShowsByCategoryUseCase>()(category)) {
                is Result.Success -> onSuccess(result.data)
                is Result.Error -> onError(result.message)
                Result.Loading -> Unit
            }
        }
    }

    fun loadFavoriteShows(onSuccess: (List<Show>) -> Unit) {
        scope.launch {
            onSuccess(get<GetFavoriteShowsUseCase>()())
        }
    }

    fun loadShowDetail(
        showId: Int,
        onSuccess: (ShowDetail) -> Unit,
        onError: (String) -> Unit
    ) {
        scope.launch {
            when (val result = get<GetShowDetailUseCase>()(showId)) {
                is Result.Success -> onSuccess(result.data)
                is Result.Error -> onError(result.message)
                Result.Loading -> Unit
            }
        }
    }

    fun loadEpisodes(
        showId: Int,
        onSuccess: (List<com.kaushikkumardevlab.kmptvshowsexplorer.domain.model.Episode>) -> Unit,
        onError: (String) -> Unit
    ) {
        scope.launch {
            when (val result = get<GetEpisodesUseCase>()(showId)) {
                is Result.Success -> onSuccess(result.data)
                is Result.Error -> onError(result.message)
                Result.Loading -> Unit
            }
        }
    }

    fun isFavoriteShow(
        showId: Int,
        onSuccess: (Boolean) -> Unit
    ) {
        scope.launch {
            onSuccess(get<IsFavoriteShowUseCase>()(showId))
        }
    }

    fun toggleFavoriteShow(
        id: Int,
        name: String,
        genres: List<String>,
        language: String?,
        imageUrl: String?,
        rating: Double?,
        onSuccess: (Boolean) -> Unit
    ) {
        scope.launch {
            val show = Show(
                id = id,
                name = name,
                genres = genres,
                language = language,
                image = Image(
                    medium = imageUrl,
                    original = imageUrl
                ),
                rating = Rating(average = rating)
            )
            onSuccess(get<ToggleFavoriteShowUseCase>()(show))
        }
    }

    fun toggleFavoriteShowDetail(
        showDetail: ShowDetail,
        onSuccess: (Boolean) -> Unit
    ) {
        toggleFavoriteShow(
            id = showDetail.id,
            name = showDetail.name,
            genres = showDetail.genres,
            language = showDetail.language,
            imageUrl = showDetail.imageUrl,
            rating = showDetail.rating,
            onSuccess = onSuccess
        )
    }

    @OptIn(KoinExperimentalAPI::class)
    private fun iosComposeWrapper(content: @Composable () -> Unit): UIViewController = ComposeUIViewController {
        val themeViewModel = koinViewModel<ThemeViewModel>()
        val isDarkThemeOverride by themeViewModel.isDarkTheme.collectAsState()
        val useDarkTheme = isDarkThemeOverride ?: isSystemInDarkTheme()

        AppTheme(useDarkTheme = useDarkTheme) {
            content()
        }
    }

    fun createMainViewController(): UIViewController = iosComposeWrapper {
        App()
    }

    fun createHomeViewController(onShowClick: (Int) -> Unit): UIViewController = iosComposeWrapper {
        val viewModel = koinViewModel<ShowListViewModel>()
        HomeScreen(viewModel = viewModel, onShowClick = onShowClick)
    }

    fun createSearchViewController(onShowClick: (Int) -> Unit): UIViewController = iosComposeWrapper {
        val viewModel = koinViewModel<ShowListViewModel>()
        CommonSearchScreen(viewModel = viewModel, onShowClick = onShowClick)
    }

    fun createCategoriesViewController(onShowClick: (Int) -> Unit): UIViewController = iosComposeWrapper {
        val viewModel = koinViewModel<ShowListViewModel>()
        CommonCategoriesScreen(viewModel = viewModel, onShowClick = onShowClick)
    }

    fun createFavoritesViewController(onShowClick: (Int) -> Unit): UIViewController = iosComposeWrapper {
        val viewModel = koinViewModel<FavoriteShowsViewModel>()
        CommonFavoritesScreen(viewModel = viewModel, onShowClick = onShowClick)
    }

    fun createAboutViewController(): UIViewController = iosComposeWrapper {
        CommonAboutScreen()
    }

    fun createDetailViewController(showId: Int, onBackClick: () -> Unit): UIViewController = iosComposeWrapper {
        val viewModel = koinViewModel<ShowDetailViewModel>()
        ShowDetailScreen(showId = showId, viewModel = viewModel, onBackClick = onBackClick)
    }

    fun createSplashViewController(onNavigateToHome: () -> Unit): UIViewController = iosComposeWrapper {
        SplashScreen(onNavigateToHome = onNavigateToHome)
    }
}
