package com.kaushikkumardevlab.kmptvshowsexplorer

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.kaushikkumardevlab.kmptvshowsexplorer.presentation.navigation.AppNavGraph

@Composable
actual fun PlatformNavGraph() {
    val navController = rememberNavController()
    // You can add your iOS-specific navigation (like a Cupertino TabBar) here
    AppNavGraph(navController = navController)
}
