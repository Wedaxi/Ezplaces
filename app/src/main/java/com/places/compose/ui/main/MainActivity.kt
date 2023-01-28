package com.places.compose.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.SideEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.places.compose.BuildConfig
import com.places.compose.ads.AdsManager
import com.places.compose.callbacks.MainEventsListener
import com.places.compose.goToDetail
import com.places.compose.ui.AutoCompleteActivityContract
import com.places.compose.ui.main.composables.AnimatedNavigation
import com.places.compose.ui.main.composables.SelectedTheme
import com.places.compose.ui.main.viewmodel.MainViewModel
import com.places.compose.ui.main.viewmodel.PreferencesViewModel
import com.places.compose.ui.theme.ComposeTheme
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalPermissionsApi
class MainActivity: ComponentActivity(), MainEventsListener {

    private val mainViewModel: MainViewModel by viewModel()
    private val preferencesViewModel: PreferencesViewModel by viewModel()

    private val adsManager: AdsManager by inject()

    private lateinit var navController: NavHostController

    private val autoComplete = registerForActivityResult(AutoCompleteActivityContract()) {
        val id = it?.id
        if (!id.isNullOrEmpty()) {
            navController.goToDetail(id)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
            navController = rememberAnimatedNavController()
            val darkTheme = when(preferencesViewModel.selectedTheme) {
                SelectedTheme.SYSTEM -> isSystemInDarkTheme()
                SelectedTheme.LIGHT -> false
                SelectedTheme.DARK -> true
            }
            ComposeTheme(darkTheme = darkTheme) {
                Surface {
                    val systemUiController = rememberSystemUiController()
                    val backgroundColor = MaterialTheme.colors.primary
                    SideEffect {
                        systemUiController.setSystemBarsColor(
                            color = backgroundColor,
                            darkIcons = false
                        )
                    }
                    AnimatedNavigation(
                        mainViewModel = mainViewModel,
                        preferencesViewModel = preferencesViewModel,
                        listener = this@MainActivity,
                        navController = navController
                    )
                }
            }
        }
    }

    override fun showRewardedAd(callback: () -> Unit) {
        if (BuildConfig.ENABLE_ADS) {
            preferencesViewModel.adCount++
            if (preferencesViewModel.adCount > 1) {
                adsManager.showRewardedAd(callback)
                preferencesViewModel.adCount = 0
            } else {
                callback()
            }
        } else {
            callback()
        }
    }

    override fun showAutoCompleteActivity() {
        autoComplete.launch(mainViewModel.lastLocation)
    }
}