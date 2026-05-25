package com.kaushikkumardevlab.kmptvshowsexplorer.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.kaushikkumardevlab.kmptvshowsexplorer.ui.theme.ThemeSettings
import kotlinx.coroutines.flow.StateFlow

class ThemeViewModel : ViewModel() {
    val isDarkTheme: StateFlow<Boolean?> = ThemeSettings.isDarkTheme

    fun toggleTheme(isDark: Boolean?) {
        ThemeSettings.setIsDarkTheme(isDark)
    }
}
