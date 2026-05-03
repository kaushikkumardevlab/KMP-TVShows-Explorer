package com.kaushikkumardevlab.kmptvshowsexplorer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kaushikkumardevlab.kmptvshowsexplorer.presentation.navigation.AppNavGraph
import com.kaushikkumardevlab.kmptvshowsexplorer.presentation.navigation.Routes

private data class AndroidTab(
    val route: String,
    val label: String,
    val icon: ImageVector
)

private val androidTabs = listOf(
    AndroidTab(Routes.SHOW_LIST, "Shows", Icons.Default.Home),
    AndroidTab("search", "Search", Icons.Default.Search),
    AndroidTab("categories", "Categories", Icons.AutoMirrored.Filled.List),
    AndroidTab("favorites", "Saved", Icons.Default.Favorite),
    AndroidTab("about", "About", Icons.Default.Info)
)

@Composable
actual fun PlatformNavGraph() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showBottomBar = currentRoute in androidTabs.map { it.route }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    androidTabs.forEach { tab ->
                        val selected = currentRoute == tab.route
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                if (currentRoute != tab.route) {
                                    navController.navigate(tab.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            icon = { Icon(tab.icon, contentDescription = tab.label) },
                            label = { Text(tab.label) }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        AppNavGraph(
            navController = navController,
            modifier = Modifier.padding(paddingValues)
        )
    }
}
