package com.places.compose.usecase

import android.graphics.Bitmap
import com.places.compose.data.model.PlaceBO
import com.places.compose.data.repository.PlacesRepository

class GetPhotoUseCase(
    private val placesRepository: PlacesRepository
): UseCase<PlaceBO, Bitmap?>() {

    override suspend fun run(params: PlaceBO): Bitmap? {
        return placesRepository.getPhoto(params)
    }
}