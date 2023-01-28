package com.places.compose.data.repository.impl

import com.places.compose.data.model.CoordinatesBO
import com.places.compose.data.model.FavoriteBO
import com.places.compose.data.model.PlaceBO
import com.places.compose.data.remote.PlacesDataSource
import com.places.compose.data.repository.PlacesRepository
import com.places.compose.data.room.AppDatabase

internal class PlacesRepositoryImpl(
    private val dataSource: PlacesDataSource,
    private val appDatabase: AppDatabase
): PlacesRepository {

    override suspend fun getLocation() = dataSource.getLocation()?.let { CoordinatesBO(it) }

    override suspend fun getLocationAvailable() = dataSource.getLocationAvailable()?.isLocationAvailable ?: false

    override suspend fun getPlaces() = dataSource.getPlaces()?.placeLikelihoods?.map { PlaceBO(it.place) }.orEmpty()

    override suspend fun getPlace(id: String) = dataSource.getPlace(id)?.let { PlaceBO(it.place) }

    override suspend fun getPhoto(place: PlaceBO) = place.photoMetadata.firstOrNull()?.let { dataSource.getPhoto(it)?.bitmap }

    override suspend fun findPlace(query: String) = dataSource.findPlace(query)?.autocompletePredictions?.map { PlaceBO(it) }.orEmpty()

    override suspend fun getFavorites() = appDatabase.favoriteDAO().getFavorites()

    override suspend fun isFavorite(id: String) = appDatabase.favoriteDAO().findById(id)

    override suspend fun addFavorite(place: PlaceBO) {
        appDatabase.placeDAO().insertAll(place)
        appDatabase.favoriteDAO().insertAll(FavoriteBO(place.id))
    }

    override suspend fun removeFavorite(place: PlaceBO) {
        appDatabase.placeDAO().delete(place)
        appDatabase.favoriteDAO().delete(FavoriteBO(place.id))
    }
}