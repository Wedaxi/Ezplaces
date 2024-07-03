package com.places.compose.di

import com.places.compose.data.model.PlaceBO
import com.places.compose.ui.main.viewmodel.*
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get(), get(), get(), get(), get()) }
    viewModel { (id: String) -> DetailViewModel(id, get(), get(), get(), get(), get()) }
    viewModel { (place: PlaceBO) -> PhotoViewModel(place, get()) }
    viewModel { CreditsViewModel(androidApplication()) }
    viewModel { PreferencesViewModel(get()) }
}