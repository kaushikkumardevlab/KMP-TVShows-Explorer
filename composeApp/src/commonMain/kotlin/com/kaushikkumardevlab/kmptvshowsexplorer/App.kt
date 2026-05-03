package com.kaushikkumardevlab.kmptvshowsexplorer

import androidx.compose.runtime.Composable
import org.koin.compose.KoinContext
import org.koin.core.annotation.KoinExperimentalAPI
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.crossfade
import com.kaushikkumardevlab.kmptvshowsexplorer.ui.theme.AppTheme

@OptIn(KoinExperimentalAPI::class)
@Composable
fun App() {
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .crossfade(enable = true)
            .build()
    }
    AppTheme {
        KoinContext {
            PlatformNavGraph()
        }
    }
}
