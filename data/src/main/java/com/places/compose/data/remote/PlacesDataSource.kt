package com.places.compose.data.remote

import android.location.Location
import com.google.android.gms.location.LocationAvailability
import com.google.android.libraries.places.api.model.PhotoMetadata
import com.google.android.libraries.places.api.net.FetchPhotoResponse
import com.google.android.libraries.places.api.net.FetchPlaceResponse
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse

interface PlacesDataSource {

    suspend fun getLocation(): Location?

    suspend fun getLocationAvailable(): LocationAvailability?

    suspend fun getPlaces(): FindCurrentPlaceResponse?

    suspend fun getPlace(id: String): FetchPlaceResponse?

    suspend fun getPhoto(photoMetadata: PhotoMetadata): FetchPhotoResponse?

    suspend fun findPlace(query: String): FindAutocompletePredictionsResponse?
}