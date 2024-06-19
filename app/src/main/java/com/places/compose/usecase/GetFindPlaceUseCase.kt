package com.places.compose.usecase

import com.places.compose.data.model.PlaceBO
import com.places.compose.data.repository.PlacesRepository

class GetFindPlaceUseCase(
    private val placesRepository: PlacesRepository
): UseCase<String, List<PlaceBO>>() {

    override suspend fun run(params: String): List<PlaceBO> {
        return placesRepository.findPlace(params)
    }
}