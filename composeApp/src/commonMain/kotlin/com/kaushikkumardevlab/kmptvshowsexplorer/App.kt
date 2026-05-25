package com.kaushikkumardevlab.kmptvshowsexplorer

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.kaushikkumardevlab.kmptvshowsexplorer.presentation.viewmodel.ThemeViewModel
import org.koin.compose.KoinContext
import org.koin.core.annotation.KoinExperimentalAPI
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.crossfade
import com.kaushikkumardevlab.kmptvshowsexplorer.ui.theme.AppTheme
import org.koin.compose.viewmodel.koinViewModel

@OptIn(KoinExperimentalAPI::class)
@Composable
fun App() {
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .crossfade(enable = true)
            .build()
    }

    KoinContext {
        val viewModel = koinViewModel<ThemeViewModel>()
        val isDarkThemeOverride by viewModel.isDarkTheme.collectAsState()
        val useDarkTheme = isDarkThemeOverride ?: isSystemInDarkTheme()

        AppTheme(useDarkTheme = useDarkTheme) {
            PlatformNavGraph()
        }
    }
}
