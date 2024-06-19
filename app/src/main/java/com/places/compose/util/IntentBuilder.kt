package com.places.compose.util

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.places.compose.buildUri

object IntentBuilder {

    const val MAPS_PACKAGE = "com.google.android.apps.maps"

    fun emailIntent(email: String) = Intent(
        Intent.ACTION_SENDTO,
        buildUri {
            scheme("mailto")
        }
    ).putExtra(Intent.EXTRA_EMAIL, arrayOf(email))

    fun locationIntent() = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)

    fun mapsIntent(latitude: Double, longitude: Double, address: String) = Intent(
        Intent.ACTION_VIEW,
        buildUri {
            scheme("geo")
            authority("$latitude,$longitude")
            appendQueryParameter("q", address)
        }
    ).setPackage(MAPS_PACKAGE)

    fun phoneIntent(phone: String) = Intent(
        Intent.ACTION_DIAL,
        buildUri {
            scheme("tel")
            authority(phone)
        }
    )

    fun playStoreIntent(packageName: String) = Intent(
        Intent.ACTION_VIEW,
        Uri.parse("http://play.google.com/store/apps/details?id=$packageName")
    ).setPackage("com.android.vending")

    fun settingsIntent(packageName: String) = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        buildUri {
            scheme("package")
            opaquePart(packageName)
        }
    )

    fun viewIntent(uri: Uri) = Intent(
        Intent.ACTION_VIEW,
        uri
    )
}