package com.places.compose.ui.main.composables

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.places.compose.callbacks.MainEventsListener
import com.places.compose.ui.main.viewmodel.MainViewModel
import com.places.compose.ui.main.viewmodel.PreferencesViewModel

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalPermissionsApi
@Composable
fun AnimatedNavigation(
    mainViewModel: MainViewModel,
    preferencesViewModel: PreferencesViewModel,
    listener: MainEventsListener,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
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