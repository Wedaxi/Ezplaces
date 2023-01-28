package com.places.compose.ui.main

sealed class AppState {

    object LoadingState: AppState()

    sealed class MainState: AppState()

    object MainTabState: MainState()
    object FavoritesTabState: MainState()

    data class DetailState(val id: String): AppState()
}
