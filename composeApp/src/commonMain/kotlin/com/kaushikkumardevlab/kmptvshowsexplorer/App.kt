package com.kaushikkumardevlab.kmptvshowsexplorer

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.kaushikkumardevlab.kmptvshowsexplorer.presentation.viewmodel.ShowListViewModel
import com.kaushikkumardevlab.kmptvshowsexplorer.ui.screen.HomeScreen
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun App() {
    MaterialTheme {
        KoinContext {
            val viewModel = koinViewModel<ShowListViewModel>()
            HomeScreen(viewModel)
        }
    }
}
