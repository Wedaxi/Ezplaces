package com.places.compose.usecase

import com.places.compose.data.model.PlaceBO
import com.places.compose.data.repository.PlacesRepository

class GetPlacesUseCase(
    private val placesRepository: PlacesRepository
): UseCase<Unit, List<PlaceBO>>() {

    override suspend fun run(params: Unit): List<PlaceBO> {
        return placesRepository.getPlaces()
    }
}