package com.places.compose.ui.main.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.places.compose.preferences.PreferencesHelper
import com.places.compose.ui.main.composables.SelectedHome
import com.places.compose.ui.main.composables.SelectedTheme

class PreferencesViewModel(
    private val preferences: PreferencesHelper
): ViewModel() {

    var selectedTheme by mutableStateOf(preferences.theme)
        private set

    var selectedHome by mutableStateOf(preferences.home)
        private set

    var adCount = preferences.adCount
        set(value) {
            field = value
            preferences.adCount = value
        }

    fun setTheme(value: SelectedTheme) {
        selectedTheme = value
        preferences.theme = value
    }

    fun setHome(value: SelectedHome) {
        selectedHome = value
        preferences.home = value
    }
}