package com.kaushikkumardevlab.kmptvshowsexplorer

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kaushikkumardevlab.kmptvshowsexplorer.presentation.navigation.AppNavGraph
import com.kaushikkumardevlab.kmptvshowsexplorer.presentation.navigation.Routes

private data class IOSTab(
    val route: String,
    val label: String,
    val icon: ImageVector
)

private val iosTabs = listOf(
    IOSTab(Routes.SHOW_LIST, "Explore", Icons.Default.Home),
    IOSTab("search", "Search", Icons.Default.Search),
    IOSTab("categories", "Library", Icons.AutoMirrored.Filled.List),
    IOSTab("favorites", "Saved", Icons.Default.Favorite),
    IOSTab("about", "Settings", Icons.Default.Info)
)

@Composable
actual fun PlatformNavGraph() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showBottomBar = currentRoute in iosTabs.map { it.route }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                // Simplified navigation bar for iOS look
                NavigationBar(
                    containerColor = Color.White.copy(alpha = 0.8f), // Mocking iOS blur effect
                    tonalElevation = 0.dp
                ) {
                    iosTabs.forEach { tab ->
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
                            icon = {
                                Icon(
                                    imageVector = tab.icon,
                                    contentDescription = tab.label,
                                    tint = if (selected) MaterialTheme.colorScheme.primary else Color.Gray
                                )
                            },
                            label = {
                                Text(
                                    text = tab.label,
                                    color = if (selected) MaterialTheme.colorScheme.primary else Color.Gray
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Color.Transparent // iOS doesn't use indicators
                            )
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
