package com.places.compose.ui.main.composables

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.places.compose.callbacks.MainEventsListener
import com.places.compose.ui.main.viewmodel.MainViewModel
import com.places.compose.ui.main.viewmodel.PreferencesViewModel

const val PERMISSION = "permission"
const val MAIN = "main"
const val DETAIL = "detail/{id}"
const val ID = "id"
const val CREDITS = "credits"
const val POLICY = "policy"

@ExperimentalMaterialApi
@ExperimentalPagerApi
@ExperimentalPermissionsApi
@Composable
fun Navigation(
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
        composable(MAIN) {
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
            )
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.getString(ID)?.let {
                LoadDetail(
                    navController = navController,
                    id = it
                )
            }
        }
    }
}