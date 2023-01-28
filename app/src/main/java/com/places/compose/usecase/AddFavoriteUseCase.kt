package com.places.compose.usecase

import com.places.compose.data.model.PlaceBO
import com.places.compose.data.repository.PlacesRepository

class AddFavoriteUseCase(
    private val placesRepository: PlacesRepository
): UseCase<PlaceBO, Unit>() {

    override suspend fun run(params: PlaceBO) {
        placesRepository.addFavorite(params)
    }
}