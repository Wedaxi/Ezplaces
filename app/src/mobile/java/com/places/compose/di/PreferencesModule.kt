package com.places.compose.di

import android.content.Context
import android.content.SharedPreferences
import com.places.compose.preferences.PreferencesHelper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val preferencesModule = module(createdAtStart = true) {

    /** Preferences **/
    single<SharedPreferences> { androidContext().getSharedPreferences("preferences", Context.MODE_PRIVATE) }
    single { PreferencesHelper(get()) }
}