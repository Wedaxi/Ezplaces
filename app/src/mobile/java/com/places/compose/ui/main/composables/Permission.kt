package com.places.compose.ui.main.composables

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.places.compose.R
import com.places.compose.util.IntentBuilder

@ExperimentalPermissionsApi
@Composable
fun LocationPermission(
    navController: NavHostController
) {
    val permissionsState = rememberMultiplePermissionsState(listOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))

    when {
        permissionsState.shouldShowRationale -> {
            Splash()
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.background)
                    .padding(10.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.permission_rationale),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(10.dp))
                SettingsButton()
            }
        }
        permissionsState.allPermissionsGranted -> {
            navController.navigate(MAIN) {
                popUpTo(PERMISSION) { inclusive = true }
            }
        }
        else -> {
            Splash()
            LaunchedEffect(permissionsState.revokedPermissions) {
                permissionsState.launchMultiplePermissionRequest()
            }
        }
    }
}

@Composable
fun SettingsButton() {
    val context = LocalContext.current
    Button(onClick = {
        val intent = IntentBuilder.settingsIntent(context.packageName)
        context.startActivity(intent)
    }) {
        Text(
            text = stringResource(id = R.string.permission_button)
        )
    }
}