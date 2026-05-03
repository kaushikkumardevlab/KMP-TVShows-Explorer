package com.kaushikkumardevlab.kmptvshowsexplorer.di

import com.kaushikkumardevlab.kmptvshowsexplorer.data.local.database.DatabaseDriverFactory
import com.kaushikkumardevlab.kmptvshowsexplorer.data.local.datasource.ShowLocalDataSource
import com.kaushikkumardevlab.kmptvshowsexplorer.data.remote.ShowApi
import com.kaushikkumardevlab.kmptvshowsexplorer.data.remote.datasource.ShowRemoteDataSource
import com.kaushikkumardevlab.kmptvshowsexplorer.data.repository.ShowRepositoryImpl
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.repository.ShowRepository
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.usecase.GetEpisodesUseCase
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.usecase.GetFavoriteShowsUseCase
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.usecase.GetShowDetailUseCase
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.usecase.GetShowsByCategoryUseCase
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.usecase.GetShowsUseCase
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.usecase.IsFavoriteShowUseCase
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.usecase.SearchShowsUseCase
import com.kaushikkumardevlab.kmptvshowsexplorer.domain.usecase.ToggleFavoriteShowUseCase
import com.kaushikkumardevlab.kmptvshowsexplorer.presentation.viewmodel.FavoriteShowsViewModel
import com.kaushikkumardevlab.kmptvshowsexplorer.presentation.viewmodel.ShowListViewModel
import com.kaushikkumardevlab.kmptvshowsexplorer.ui.screen.detail.ShowDetailViewModel
import com.kaushikkumardevlab.kmptvshowsexplorer.data.local.database.ShowDatabase
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val networkModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                })
            }
        }
    }
    singleOf(::ShowApi)
}

val dataModule = module {
    // Database
    single {
        val driver = get<DatabaseDriverFactory>().createDriver()
        ShowDatabase(driver)
    }

    // Data Sources
    singleOf(::ShowRemoteDataSource)
    singleOf(::ShowLocalDataSource)
    
    // Repository
    singleOf(::ShowRepositoryImpl) bind ShowRepository::class
}

val domainModule = module {
    factoryOf(::GetShowsUseCase)
    factoryOf(::SearchShowsUseCase)
    factoryOf(::GetShowsByCategoryUseCase)
    factoryOf(::GetShowDetailUseCase)
    factoryOf(::GetEpisodesUseCase)
    factoryOf(::GetFavoriteShowsUseCase)
    factoryOf(::IsFavoriteShowUseCase)
    factoryOf(::ToggleFavoriteShowUseCase)
}

val viewModelModule = module {
    factoryOf(::ShowListViewModel)
    factoryOf(::ShowDetailViewModel)
    factoryOf(::FavoriteShowsViewModel)
}

fun appModule() = listOf(
    networkModule,
    dataModule,
    domainModule,
    viewModelModule
)
