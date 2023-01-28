package com.places.compose.ui.main.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.places.compose.data.model.CoordinatesBO
import com.places.compose.data.model.PlaceBO
import com.places.compose.usecase.*
import timber.log.Timber

class MainViewModel(
    private val getLocationUseCase: GetLocationUseCase,
    private val getLocationAvailableUseCase: GetLocationAvailableUseCase,
    private val getPlacesUseCase: GetPlacesUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val getFindPlaceUseCase: GetFindPlaceUseCase
): ViewModel() {

    var lastLocation: CoordinatesBO? by mutableStateOf(null)
        private set

    var locationAvailable by mutableStateOf(true)
        private set

    var places by mutableStateOf<List<PlaceBO>?>(null)
        private set

    var favorites by mutableStateOf<List<PlaceBO>?>(null)
        private set

    var isRefreshing by mutableStateOf(false)
        private set

    init {
        isLocationAvailable { isAvailable ->
            if (isAvailable) {
                getLastLocation {
                    getPlaces()
                    getFavorites()
                }
            } else {
                places = emptyList()
                getFavorites()
            }
        }
    }

    private fun getPlaces() {
        getPlacesUseCase.invoke(scope = viewModelScope, params = Unit) {
            places = it
            isRefreshing = false
        }
    }

    private fun getFavorites() {
        getFavoritesUseCase.invoke(scope = viewModelScope, params = Unit) {
            favorites = it
            isRefreshing = false
        }
    }

    private fun getLastLocation(callback: () -> Unit) {
        getLocationUseCase.invoke(scope = viewModelScope, params = Unit) { location ->
            lastLocation?.let {
                if (location != null && it.calculateDistance(location) > 250) {
                    callback()
                } else {
                    isRefreshing = false
                }
                lastLocation = location
            } ?: run {
                callback()
                lastLocation = location
            }
        }
    }

    private fun isLocationAvailable(callback: (Boolean) -> Unit) {
        locationAvailable = true
        getLocationAvailableUseCase.invoke(scope = viewModelScope, params = Unit) {
            locationAvailable = it
            callback(it)
        }
    }

    fun refresh(page: Int) {
        isRefreshing = true
        if (page == 0) {
            isLocationAvailable { isAvailable ->
                if (isAvailable) {
                    getLastLocation {
                        getPlaces()
                    }
                } else {
                    isRefreshing = false
                }
            }
        } else {
            getFavorites()
        }
    }
}