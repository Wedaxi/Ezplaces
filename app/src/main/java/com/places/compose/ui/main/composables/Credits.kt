package com.places.compose.ui.main.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import com.places.compose.R
import com.places.compose.data.model.CreditBO
import com.places.compose.ui.main.viewmodel.CreditsViewModel
import com.places.compose.ui.theme.ComposeTheme
import com.places.compose.util.IntentBuilder
import org.koin.androidx.compose.getViewModel

@Composable
fun Credits(
    navController: NavHostController,
    viewModel: CreditsViewModel = getViewModel()
) {
    val credits = viewModel.credits
    Scaffold(
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                title = {
                    Text(
                        text = stringResource(id = R.string.app_credits),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colors.onPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            items(credits) {
                Credit(it)
            }
        }
    }
}

@Composable
fun Credit(credit: CreditBO) {
    val context = LocalContext.current
    RowItem(
        onCLick = {
            val intent = IntentBuilder.viewIntent(credit.url.toUri())
            context.startActivity(intent)
        }
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = credit.name)
                Text(text = credit.author)
            }
            Divider(modifier = Modifier.padding(top = 1.dp))
            Text(text = credit.description)
            Divider(modifier = Modifier.padding(top = 1.dp))
            Text(
                text = credit.license,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        val intent = IntentBuilder.viewIntent(credit.licenseUrl.toUri())
                        context.startActivity(intent)
                    }
            )
        }
    }
}

@Preview
@Composable
fun CreditPreview() {
    ComposeTheme {
        Surface(color = MaterialTheme.colors.background) {
            Credit(
                CreditBO(
                    name = "Accompanist",
                    author = "Google",
                    description = "Accompanist is a group of libraries that aim to supplement Jetpack Compose with features that are commonly required by developers but not yet available.",
                    url = "https://github.com/google/accompanist",
                    license = "Apache License 2.0",
                    licenseUrl = "https://raw.githubusercontent.com/google/accompanist/main/LICENSE"
                )
            )
        }
    }
}

@Preview
@Composable
fun CreditPreviewDark() {
    ComposeTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colors.background) {
            Credit(
                CreditBO(
                    name = "Accompanist",
                    author = "Google",
                    description = "Accompanist is a group of libraries that aim to supplement Jetpack Compose with features that are commonly required by developers but not yet available.",
                    url = "https://github.com/google/accompanist",
                    license = "Apache License 2.0",
                    licenseUrl = "https://raw.githubusercontent.com/google/accompanist/main/LICENSE"
                )
            )
        }
    }
}