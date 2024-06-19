package com.places.compose

import android.app.Application
import com.places.compose.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@App)

            val moduleList = listOf(
                placesModule,
                networkModule,
                useCaseModule,
                viewModelModule
            )
            modules(moduleList)
        }
    }
}