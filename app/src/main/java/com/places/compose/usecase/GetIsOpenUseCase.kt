package com.places.compose.usecase

import com.places.compose.data.repository.PlacesRepository

class GetIsOpenUseCase(
    private val placesRepository: PlacesRepository
): UseCase<String, Boolean>() {

    override suspend fun run(params: String): Boolean {
        return placesRepository.isOpen(params)
    }
}