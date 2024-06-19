package com.places.compose.callbacks

fun interface ShowAdListener {
    fun showRewardedAd(callback: () -> Unit)
}