package com.places.compose.ads

import android.content.Context
import android.view.View

class AdsManager {

    fun buildAdView(context: Context): View = throw RuntimeException("Ads are disabled")

    inline fun showRewardedAd(callback: () -> Unit) = callback()
}