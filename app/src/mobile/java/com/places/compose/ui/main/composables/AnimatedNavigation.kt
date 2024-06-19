package com.places.compose.ui.main.composables

import androidx.compose.animation.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.places.compose.callbacks.MainEventsListener
import com.places.compose.ui.main.viewmodel.MainViewModel
import com.places.compose.ui.main.viewmodel.PreferencesViewModel

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalPermissionsApi
@Composable
fun AnimatedNavigation(
    mainViewModel: MainViewModel,
    preferencesViewModel: PreferencesViewModel,
    listener: MainEventsListener,
    navController: NavHostController = rememberAnimatedNavController()
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = PERMISSION
    ) {
        composable(
            route = PERMISSION
        ) {
            LocationPermission(
                navController = navController
            )
        }
        composable(
            route = MAIN
        ) {
            LoadPlaces(
                viewModel = mainViewModel,
                preferencesViewModel = preferencesViewModel,
                listener = listener,
                navController = navController
            )
        }
        composable(
            route = DETAIL,
            arguments = listOf(
                navArgument(ID) {
                    type = NavType.StringType
                }
            ),
            enterTransition = {
                slideInHorizontally(initialOffsetX = { it / 2 })
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { it / 2 })
            }
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.getString(ID)?.let {
                LoadDetail(
                    navController = navController,
                    id = it
                )
            }
        }
        composable(
            route = CREDITS,
            enterTransition = {
                slideInHorizontally(initialOffsetX = { it / 2 })
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { it / 2 })
            }
        ) {
            Credits(
                navController = navController
            )
        }
        composable(
            route = POLICY,
            enterTransition = {
                slideInHorizontally(initialOffsetX = { it / 2 })
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { it / 2 })
            }
        ) {
            PrivacyPolicyWebView()
        }
    }
}