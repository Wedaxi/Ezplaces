package com.places.compose.preferences

import android.content.SharedPreferences
import com.places.compose.ui.main.composables.SelectedHome
import com.places.compose.ui.main.composables.SelectedTheme

private const val THEME_KEY = "theme"
private const val HOME_KEY = "home"
private const val AD_COUNT_KEY = "ad_count"

class PreferencesHelper(
    private val sharedPreferences: SharedPreferences
) {

    var theme: SelectedTheme
        get() = SelectedTheme.values()[sharedPreferences.getInt(THEME_KEY, 0)]
        set(value) {
            sharedPreferences.edit().putInt(THEME_KEY, value.ordinal).apply()
        }

    var home: SelectedHome
        get() = SelectedHome.values()[sharedPreferences.getInt(HOME_KEY, 0)]
        set(value) {
            sharedPreferences.edit().putInt(HOME_KEY, value.ordinal).apply()
        }

    var adCount: Int
        get() = sharedPreferences.getInt(AD_COUNT_KEY, 0)
        set(value) {
            sharedPreferences.edit().putInt(AD_COUNT_KEY, value).apply()
        }
}