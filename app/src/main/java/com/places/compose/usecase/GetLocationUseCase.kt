package com.places.compose.usecase

import com.places.compose.data.model.CoordinatesBO
import com.places.compose.data.repository.PlacesRepository

class GetLocationUseCase(
    private val placesRepository: PlacesRepository
): UseCase<Unit, CoordinatesBO?>() {

    override suspend fun run(params: Unit): CoordinatesBO? {
        return placesRepository.getLocation()
    }
}