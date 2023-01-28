package com.places.compose.ads

import android.app.Activity
import android.content.Context
import android.view.View
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class AdsManager(private val activity: Activity) {

    private var interstitialAd: InterstitialAd? = null

    init {
        MobileAds.initialize(activity)
        loadRewardedAd()
    }

    fun buildAdView(context: Context): View = AdView(context).apply {
        setAdSize(AdSize.BANNER) // AdSize.getInlineAdaptiveBannerAdSize(AdSize.FULL_WIDTH, 56)
        adUnitId = BuildConfig.BANNER_UNIT_ID
        val adRequest = AdRequest.Builder().build()
        loadAd(adRequest)
    }

    fun showRewardedAd(callback: () -> Unit) {
        interstitialAd?.apply {
            fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdShowedFullScreenContent() {
                    interstitialAd = null
                    loadRewardedAd()
                }

                override fun onAdDismissedFullScreenContent() {
                    callback()
                }

                override fun onAdFailedToShowFullScreenContent(error: AdError) {
                    callback()
                    interstitialAd = null
                    loadRewardedAd()
                }
            }
            show(activity)
        } ?: run {
            callback()
            loadRewardedAd()
        }
    }

    private fun loadRewardedAd() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            activity,
            BuildConfig.REWARDED_UNIT_ID,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(error: LoadAdError) {
                    interstitialAd = null
                }

                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                }
            }
        )
    }
}