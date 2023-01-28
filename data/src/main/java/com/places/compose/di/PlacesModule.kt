package com.places.compose.di

import androidx.room.Room
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient
import com.places.compose.data.BuildConfig
import com.places.compose.data.room.AppDatabase
import com.places.compose.decrypt
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val placesModule = module(createdAtStart = true) {
    single<PlacesClient> {
        Places.initialize(androidContext(), BuildConfig.ENCRYPTED_API_KEY.decrypt().orEmpty())
        Places.createClient(androidContext())
    }
    single<FusedLocationProviderClient> {
        LocationServices.getFusedLocationProviderClient(androidContext())
    }
    single<AppDatabase> {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "favorites").build()
    }
}