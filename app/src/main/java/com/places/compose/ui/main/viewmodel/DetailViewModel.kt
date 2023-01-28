package com.places.compose.ui.main.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.places.compose.data.model.PlaceBO
import com.places.compose.usecase.*

class DetailViewModel(
    id: String,
    getPlaceUseCase: GetPlaceUseCase,
    getFavoriteUseCase: GetFavoriteUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val removeFavoriteUseCase: RemoveFavoriteUseCase
): ViewModel() {

    var isFavorite by mutableStateOf(false)
        private set

    var place by mutableStateOf<PlaceBO?>(null)
        private set

    init {
        getPlaceUseCase.invoke(scope = viewModelScope, params = id) {
            place = it
        }
        getFavoriteUseCase.invoke(scope = viewModelScope, params = id) {
            isFavorite = it
        }
    }

    fun setFavorite() {
        place?.let {
            isFavorite = if (isFavorite) {
                removeFavoriteUseCase.invoke(scope = viewModelScope, params = it)
                false
            } else {
                addFavoriteUseCase.invoke(scope = viewModelScope, params = it)
                true
            }
        }
    }
}