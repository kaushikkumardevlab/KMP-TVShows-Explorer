package com.kaushikkumardevlab.kmptvshowsexplorer.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kaushikkumardevlab.kmptvshowsexplorer.presentation.viewmodel.FavoriteShowsViewModel
import com.kaushikkumardevlab.kmptvshowsexplorer.presentation.viewmodel.ShowListViewModel
import com.kaushikkumardevlab.kmptvshowsexplorer.ui.screen.HomeScreen
import com.kaushikkumardevlab.kmptvshowsexplorer.ui.screen.SplashScreen
import com.kaushikkumardevlab.kmptvshowsexplorer.ui.screen.CommonSearchScreen
import com.kaushikkumardevlab.kmptvshowsexplorer.ui.screen.CommonCategoriesScreen
import com.kaushikkumardevlab.kmptvshowsexplorer.ui.screen.CommonFavoritesScreen
import com.kaushikkumardevlab.kmptvshowsexplorer.ui.screen.CommonAboutScreen
import com.kaushikkumardevlab.kmptvshowsexplorer.ui.screen.detail.ShowDetailScreen
import com.kaushikkumardevlab.kmptvshowsexplorer.ui.screen.detail.ShowDetailViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH,
        modifier = modifier.fillMaxSize()
    ) {
        composable(Routes.SPLASH) {
            SplashScreen(
                onNavigateToHome = {
                    navController.navigate(Routes.SHOW_LIST) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.SHOW_LIST) {
            val viewModel = koinViewModel<ShowListViewModel>()
            HomeScreen(
                viewModel = viewModel,
                onShowClick = { id -> navController.navigate("show_detail/$id") }
            )
        }

        composable("search") {
            val viewModel = koinViewModel<ShowListViewModel>()
            CommonSearchScreen(
                viewModel = viewModel,
                onShowClick = { id -> navController.navigate("show_detail/$id") }
            )
        }

        composable("categories") {
            val viewModel = koinViewModel<ShowListViewModel>()
            CommonCategoriesScreen(
                viewModel = viewModel,
                onShowClick = { id -> navController.navigate("show_detail/$id") }
            )
        }

        composable("favorites") {
            val viewModel = koinViewModel<FavoriteShowsViewModel>()
            CommonFavoritesScreen(
                viewModel = viewModel,
                onShowClick = { id -> navController.navigate("show_detail/$id") }
            )
        }

        composable("about") {
            CommonAboutScreen()
        }

        composable(
            route = Routes.SHOW_DETAIL,
            arguments = listOf(
                navArgument("showId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val showId = backStackEntry.arguments?.getInt("showId") ?: 0
            val viewModel = koinViewModel<ShowDetailViewModel>()
            ShowDetailScreen(
                showId = showId,
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
