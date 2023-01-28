package com.places.compose.data.repository

import android.graphics.Bitmap
import com.places.compose.data.model.CoordinatesBO
import com.places.compose.data.model.PlaceBO

interface PlacesRepository {

    suspend fun getLocation(): CoordinatesBO?

    suspend fun getLocationAvailable(): Boolean

    suspend fun getPlaces(): List<PlaceBO>

    suspend fun getPlace(id: String): PlaceBO?

    suspend fun getPhoto(place: PlaceBO): Bitmap?

    suspend fun findPlace(query: String): List<PlaceBO>

    suspend fun getFavorites(): List<PlaceBO>

    suspend fun isFavorite(id: String): Boolean

    suspend fun addFavorite(place: PlaceBO)

    suspend fun removeFavorite(place: PlaceBO)
}