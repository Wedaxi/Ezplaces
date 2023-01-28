package com.places.compose.di

import com.places.compose.data.remote.PlacesDataSource
import com.places.compose.data.remote.impl.PlacesDataSourceImpl
import com.places.compose.data.repository.PlacesRepository
import com.places.compose.data.repository.impl.PlacesRepositoryImpl
import org.koin.dsl.module

val networkModule = module {
    factory<PlacesRepository> { PlacesRepositoryImpl(get(), get()) }
    factory<PlacesDataSource> { PlacesDataSourceImpl(get(), get()) }
}