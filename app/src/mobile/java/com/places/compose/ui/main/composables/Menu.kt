package com.places.compose.ui.main.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.places.compose.BuildConfig
import com.places.compose.R
import com.places.compose.preferences.PreferencesHelper
import com.places.compose.ui.main.viewmodel.PreferencesViewModel
import com.places.compose.ui.theme.ComposeTheme
import com.places.compose.util.IntentBuilder

enum class SelectedTheme {
    SYSTEM,
    LIGHT,
    DARK;
}

enum class SelectedHome {
    PLACES,
    FAVORITES;
}

@Composable
fun Menu(
    preferencesViewModel: PreferencesViewModel,
    navController: NavHostController
) {
    val context = LocalContext.current
    ColumnItem(
        withBorder = false,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Divider()
        Theme(preferencesViewModel = preferencesViewModel)
        Divider()
        Start(preferencesViewModel = preferencesViewModel)
        Divider()
        Rate {
            val storeIntent = IntentBuilder.playStoreIntent(context.packageName)
            context.startActivity(storeIntent)
        }
        Divider()
        Email {
            val emailIntent = IntentBuilder.emailIntent("wedaxi@pm.me")
            context.startActivity(emailIntent)
        }
        Divider()
        Privacy {
            navController.navigate(POLICY)
        }
        Divider()
        About {
            navController.navigate(CREDITS)
        }
        Divider()
        Build()
        Divider()
    }
}

@Composable
fun Theme(
    preferencesViewModel: PreferencesViewModel
) {
    SwitchMenu(
        text = stringResource(id = R.string.menu_theme),
        icon = Icons.Default.Settings
    ) {
        ThemeSwitchItems(viewModel = preferencesViewModel)
    }
}

@Composable
fun Start(
    preferencesViewModel: PreferencesViewModel
) {
    SwitchMenu(
        text = stringResource(id = R.string.menu_home),
        icon = Icons.Default.Home
    ) {
        StartSwitchItems(viewModel = preferencesViewModel)
    }
}

@Composable
fun About(
    onClick: () -> Unit
) {
    RadioButtonMenu(
        text = stringResource(id = R.string.menu_credits),
        icon = Icons.AutoMirrored.Filled.List,
        onCLick = onClick
    )
}

@Composable
fun Privacy(
    onClick: () -> Unit
) {
    RadioButtonMenu(
        text = stringResource(id = R.string.menu_privacy),
        icon = Icons.Default.Info,
        onCLick = onClick
    )
}

@Composable
fun Rate(
    onClick: () -> Unit
) {
    RadioButtonMenu(
        text = stringResource(id = R.string.menu_rate),
        icon = Icons.Default.Star,
        onCLick = onClick
    )
}

@Composable
fun Email(
    onClick: () -> Unit
) {
    RadioButtonMenu(
        text = stringResource(id = R.string.menu_contact),
        icon = Icons.Default.Email,
        onCLick = onClick
    )
}

@Composable
fun Build() {
    RadioButtonMenu(
        text = stringResource(R.string.menu_version, BuildConfig.VERSION_NAME),
        icon = Icons.Default.Build
    )
}

@Composable
fun RadioButtonMenu(
    text: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onCLick: (() -> Unit)? = null
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable(enabled = onCLick != null) {
                onCLick?.invoke()
            }
            .padding(vertical = 15.dp, horizontal = 5.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.padding(end = 15.dp)
        )
        Text(
            text = text
        )
    }
}

@Composable
fun SwitchMenu(
    text: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp, horizontal = 5.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.padding(end = 15.dp)
            )
            Text(
                text = text
            )
        }
        content()
    }
}

@Composable
fun ThemeSwitchItems(
    viewModel: PreferencesViewModel
) {
    RadioButtonItem(
        name = stringResource(id = R.string.menu_theme_system),
        checked = viewModel.selectedTheme == SelectedTheme.SYSTEM,
        onCheckedChange = {
            if (it) {
                viewModel.setTheme(SelectedTheme.SYSTEM)
            }
        }
    )
    RadioButtonItem(
        name = stringResource(id = R.string.menu_theme_light),
        checked = viewModel.selectedTheme == SelectedTheme.LIGHT,
        onCheckedChange = {
            if (it) {
                viewModel.setTheme(SelectedTheme.LIGHT)
            }
        }
    )
    RadioButtonItem(
        name = stringResource(id = R.string.menu_theme_dark),
        checked = viewModel.selectedTheme == SelectedTheme.DARK,
        onCheckedChange = {
            if (it) {
                viewModel.setTheme(SelectedTheme.DARK)
            }
        }
    )
}

@Composable
fun StartSwitchItems(
    viewModel: PreferencesViewModel
) {
    RadioButtonItem(
        name = stringResource(id = R.string.menu_home_places),
        checked = viewModel.selectedHome == SelectedHome.PLACES,
        onCheckedChange = {
            if (it) {
                viewModel.setHome(SelectedHome.PLACES)
            }
        }
    )
    RadioButtonItem(
        name = stringResource(id = R.string.menu_home_favorites),
        checked = viewModel.selectedHome == SelectedHome.FAVORITES,
        onCheckedChange = {
            if (it) {
                viewModel.setHome(SelectedHome.FAVORITES)
            }
        }
    )
}

@Composable
fun RadioButtonItem(
    name: String,
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = checked,
            onClick = {
                onCheckedChange(!checked)
            }
        )
        Text(text = name)
    }
}

@Preview
@Composable
fun MenuPreview() {
    ComposeTheme {
        Surface(color = MaterialTheme.colors.background) {
            Menu(navController = rememberNavController(), preferencesViewModel = PreferencesViewModel(PreferencesHelper(LocalContext.current.getSharedPreferences("", 0))))
        }
    }
}

@Preview
@Composable
fun MenuPreviewDark() {
    ComposeTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colors.background) {
            Menu(navController = rememberNavController(), preferencesViewModel = PreferencesViewModel(PreferencesHelper(LocalContext.current.getSharedPreferences("", 0))))
        }
    }
}
