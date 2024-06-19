package com.places.compose.ui.main.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.places.compose.ads.AdsManager
import com.places.compose.BuildConfig
import org.koin.androidx.compose.get

@Composable
fun AdsBanner(
    modifier: Modifier = Modifier,
    adsManager: AdsManager = get()
) {
    if (BuildConfig.ENABLE_ADS) {
        Box(
            modifier = modifier
                .height(50.dp)
                .fillMaxWidth()
        ) {
            AndroidView(
                factory = { context ->
                    adsManager.buildAdView(context)
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}