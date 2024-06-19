package com.places.compose.ui.main.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.items
import coil.compose.rememberAsyncImagePainter
import com.places.compose.data.model.CoordinatesBO
import com.places.compose.data.model.PlaceBO
import com.places.compose.R
import com.places.compose.ui.main.viewmodel.MainViewModel

@Composable
fun WearApp(viewModel: MainViewModel) {
    Places(
        places = viewModel.places,
        location = viewModel.lastLocation
    )
}

@Composable
fun Places(
    places: List<PlaceBO>?,
    location: CoordinatesBO? = null
) {
    AnimatedVisibility(
        visible = !places.isNullOrEmpty(),
        enter = slideInVertically(),
        exit = slideOutVertically()
    ) {
        ScalingLazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            items(items = places.orEmpty(), key = { it.id }) { place ->
                RowItem {
                    Icon(
                        place = place,
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .size(25.dp)
                    )
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = place.name,
                            fontSize = 12.sp
                        )
                        if (location != null) {
                            place.coordinates?.let { coordinates ->
                                val distance = location.calculateDistance(coordinates)
                                Text(
                                    text = stringResource(R.string.distance_meters, distance),
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Icon(place: PlaceBO, modifier: Modifier = Modifier) {
    val color = place.iconBackground?.let { Color(it) }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.background(color = color ?: Color.Transparent, shape = CircleShape)
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = place.icon),
            contentDescription = null,
            modifier = Modifier.size(18.dp)
        )
    }
}