package com.places.compose.usecase

import com.places.compose.data.repository.PlacesRepository

class GetLocationAvailableUseCase(
    private val placesRepository: PlacesRepository
): UseCase<Unit, Boolean>() {

    override suspend fun run(params: Unit): Boolean {
        return placesRepository.getLocationAvailable()
    }
}