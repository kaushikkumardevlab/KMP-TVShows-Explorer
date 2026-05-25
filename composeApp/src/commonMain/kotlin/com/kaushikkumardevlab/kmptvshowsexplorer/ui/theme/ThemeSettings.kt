package com.kaushikkumardevlab.kmptvshowsexplorer.ui.theme

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object ThemeSettings {
    private val _isDarkTheme = MutableStateFlow<Boolean?>(null) // null means follow system
    val isDarkTheme: StateFlow<Boolean?> = _isDarkTheme.asStateFlow()

    fun setIsDarkTheme(isDark: Boolean?) {
        _isDarkTheme.value = isDark
    }
}
