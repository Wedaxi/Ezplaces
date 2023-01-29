package com.places.compose.data.remote.impl

import android.annotation.SuppressLint
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import com.google.android.libraries.places.api.model.PhotoMetadata
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPhotoRequest
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.places.compose.data.remote.PlacesDataSource
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

internal class PlacesDataSourceImpl(
    private val placesClient: PlacesClient,
    private val fusedLocationClient: FusedLocationProviderClient
): PlacesDataSource {

    private suspend fun <T> Task<T>.await(): T = suspendCoroutine { continuation ->
        addOnCompleteListener { task ->
            if (task.isSuccessful) {
                continuation.resume(task.result)
            } else {
                continuation.resumeWithException(task.exception ?: RuntimeException("Unknown task exception"))
            }
        }
    }

    private suspend fun <T> getData(task: Task<T>): T? = runCatching {
        task.await()
    }.onFailure {
        Log.e("PlacesDataSourceImpl", it.toString())
    }.getOrNull()

    @SuppressLint("MissingPermission")
    override suspend fun getLocation() = getData(fusedLocationClient.lastLocation)

    @SuppressLint("MissingPermission")
    override suspend fun getLocationAvailable() = getData(fusedLocationClient.locationAvailability)

    @SuppressLint("MissingPermission")
    override suspend fun getPlaces() = getData(
        placesClient.findCurrentPlace(
            FindCurrentPlaceRequest.newInstance(
                listOf(
                    Place.Field.ID,
                    Place.Field.NAME,
                    // Place.Field.ADDRESS,
                    Place.Field.LAT_LNG,
                    Place.Field.ICON_URL,
                    Place.Field.ICON_BACKGROUND_COLOR
                )
            )
        )
    )

    override suspend fun getPlace(id: String) = getData(
        placesClient.fetchPlace(
            FetchPlaceRequest.newInstance(
                id,
                listOf(
                    Place.Field.ID,
                    Place.Field.NAME,
                    Place.Field.ADDRESS,
                    Place.Field.LAT_LNG,
                    Place.Field.ICON_URL,
                    Place.Field.ICON_BACKGROUND_COLOR,
                    Place.Field.PHONE_NUMBER,
                    Place.Field.WEBSITE_URI,
                    Place.Field.OPENING_HOURS,
                    Place.Field.RATING,
                    Place.Field.USER_RATINGS_TOTAL,
                    Place.Field.PHOTO_METADATAS
                )
            )
        )
    )

    override suspend fun getPhoto(photoMetadata: PhotoMetadata) = getData(
        placesClient.fetchPhoto(
            FetchPhotoRequest.newInstance(photoMetadata)
        )
    )

    override suspend fun findPlace(query: String) = getData(
        placesClient.findAutocompletePredictions(
            FindAutocompletePredictionsRequest.builder()
                .setQuery(query)
                .setOrigin(getLocation()?.let { LatLng(it.latitude, it.longitude) })
                .build()
        )
    )
}
