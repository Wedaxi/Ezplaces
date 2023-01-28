package com.places.compose.usecase

import com.places.compose.data.model.PlaceBO
import com.places.compose.data.repository.PlacesRepository

class GetPlaceUseCase(
    private val placesRepository: PlacesRepository
): UseCase<String, PlaceBO?>() {

    override suspend fun run(params: String): PlaceBO? {
        return placesRepository.getPlace(params)
    }
}