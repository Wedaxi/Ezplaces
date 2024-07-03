package com.places.compose.di

import com.places.compose.usecase.*
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetLocationUseCase(get()) }
    factory { GetLocationAvailableUseCase(get()) }
    factory { GetPlacesUseCase(get()) }
    factory { GetPlaceUseCase(get()) }
    factory { GetPhotoUseCase(get()) }
    factory { GetFavoritesUseCase(get()) }
    factory { GetFavoriteUseCase(get()) }
    factory { AddFavoriteUseCase(get()) }
    factory { RemoveFavoriteUseCase(get()) }
    factory { GetFindPlaceUseCase(get()) }
    factory { GetIsOpenUseCase(get()) }
}