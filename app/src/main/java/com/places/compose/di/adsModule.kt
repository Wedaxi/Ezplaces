package com.places.compose.di

import com.places.compose.ads.AdsManager
import org.koin.dsl.module

val adsModule = module {
    single { AdsManager() }
}
