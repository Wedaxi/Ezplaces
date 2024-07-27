package com.places.compose.ui.main.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.places.compose.R

@Composable
fun RowItem(
    modifier: Modifier = Modifier,
    withBorder: Boolean = true,
    shape: Shape = MaterialTheme.shapes.medium,
    onCLick: (() -> Unit)? = null,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding))
            .run {
                if (withBorder) {
                    border(
                        width = 1.dp,
                        color = MaterialTheme.colors.onBackground,
                        shape = shape
                    )
                } else {
                    this
                }
            }
            .fillMaxWidth()
            .clickable(enabled = onCLick != null) {
                onCLick?.invoke()
            }
            .padding(vertical = 5.dp, horizontal = 10.dp)
    ) {
        content()
    }
}

@Composable
fun ColumnItem(
    modifier: Modifier = Modifier,
    withBorder: Boolean = true,
    shape: Shape = MaterialTheme.shapes.medium,
    onCLick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.padding))
            .run {
                if (withBorder) {
                    border(
                        width = 1.dp,
                        color = MaterialTheme.colors.onBackground,
                        shape = shape
                    )
                } else {
                    this
                }
            }
            .fillMaxWidth()
            .clickable(enabled = onCLick != null) {
                onCLick?.invoke()
            }
            .padding(vertical = 5.dp, horizontal = 10.dp)
    ) {
        content()
    }
}

@Composable
fun ShowSnack(
    snackbarHostState: SnackbarHostState,
    show: Boolean,
    message: String,
    actionLabel: String? = null,
    actionPerformed: (() -> Unit)? = null
) {
    var snackbarResult by remember { mutableStateOf(SnackbarResult.Dismissed) }
    if (snackbarResult == SnackbarResult.ActionPerformed && actionPerformed != null) {
        LaunchedEffect(snackbarResult) {
            actionPerformed()
        }
    }
    if (show) {
        LaunchedEffect(snackbarHostState) {
            snackbarResult = snackbarHostState.showSnackbar(
                message = message,
                actionLabel = actionLabel
            )
        }
    }
}