package com.places.compose.usecase

import com.places.compose.data.model.PlaceBO
import com.places.compose.data.repository.PlacesRepository

class RemoveFavoriteUseCase(
    private val placesRepository: PlacesRepository
): UseCase<PlaceBO, Unit>() {

    override suspend fun run(params: PlaceBO) {
        placesRepository.removeFavorite(params)
    }
}