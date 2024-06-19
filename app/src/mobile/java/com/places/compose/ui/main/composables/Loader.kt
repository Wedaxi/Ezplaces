package com.places.compose.ui.main.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.places.compose.ui.theme.ComposeTheme

@Composable
fun Loader() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colors.secondary,
            strokeWidth = 5.dp,
            modifier = Modifier.size(60.dp)
        )
    }
}

@Preview
@Composable
fun LoaderPreview() {
    ComposeTheme {
        Surface(color = MaterialTheme.colors.background) {
            Loader()
        }
    }
}