package com.places.compose.ui.main.composables

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.places.compose.R
import com.places.compose.data.model.CoordinatesBO
import com.places.compose.data.model.PlaceBO
import com.places.compose.startMapsOrPlayStore
import com.places.compose.ui.main.viewmodel.DetailViewModel
import com.places.compose.ui.main.viewmodel.PhotoViewModel
import com.places.compose.ui.theme.ComposeTheme
import com.places.compose.util.IntentBuilder
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

@ExperimentalFoundationApi
@Composable
fun LoadDetail(
    id: String,
    navController: NavHostController,
    viewModel: DetailViewModel = getViewModel { parametersOf(id) }
) {
    Detail(
        place = viewModel.place,
        navController = navController,
        isFavorite = viewModel.isFavorite,
        isOpen = viewModel.isOpen,
        onFavorite = { viewModel.setFavorite() }
    )
}

enum class DragValue { Start, End }

@ExperimentalFoundationApi
@Composable
fun Detail(
    place: PlaceBO?,
    navController: NavHostController,
    isFavorite: Boolean,
    isOpen: Boolean,
    onFavorite: () -> Unit
) {
    val density = LocalDensity.current
    val scaffoldState = rememberScaffoldState()
    var collapsedHeight by remember { mutableFloatStateOf( 0F) }
    var photoHeight by remember { mutableFloatStateOf( 0F) }
    val draggableState = remember {
        AnchoredDraggableState(
            initialValue = DragValue.Start,
            positionalThreshold = { distance -> distance * 0.5f },
            velocityThreshold = { with(density) { 125.dp.toPx() } },
            animationSpec = SpringSpec<Float>()
        )
    }
    val yOffset = runCatching { draggableState.requireOffset() }.getOrElse { 0F }
    val offset = IntOffset(x = 0, y = yOffset.roundToInt())
    val relativeOffset = yOffset / -collapsedHeight
    val alpha = min(1F, max(0F, relativeOffset)).takeIf { !it.isNaN() } ?: 1F
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            Box {
                if (place != null && place.hasPhotoMetadata) {
                    Photo(
                        place = place,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1F)
                            .offset { offset }
                            .onSizeChanged { size ->
                                photoHeight = size.height.toFloat()
                                collapsedHeight = photoHeight - with(density) { 56.dp.toPx() }
                                draggableState.updateAnchors(
                                    DraggableAnchors {
                                        DragValue.Start at 0f
                                        DragValue.End at -collapsedHeight
                                    }
                                )
                            }
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            // .height(with(density) { (photoHeight + yOffset).toDp() })
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        MaterialTheme.colors.primary,
                                        MaterialTheme.colors.primary.copy(alpha = alpha)
                                    )
                                )
                            )
                    )
                }
                TopAppBar(
                    title = {
                        AnimatedVisibility(
                            visible = place?.name != null,
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            Text(
                                text = place?.name.orEmpty(),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = MaterialTheme.colors.onPrimary
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null,
                                tint = MaterialTheme.colors.onPrimary
                            )
                        }
                    },
                    backgroundColor = MaterialTheme.colors.primary.copy(alpha = if (alpha < 1F) 0F else 1F),
                    elevation = 0.dp
                )
            }
        },
        bottomBar = {
            AdsBanner()
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onFavorite,
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = if (isFavorite) MaterialTheme.colors.secondary else MaterialTheme.colors.onPrimary
                )
            }
        },
        modifier = Modifier.anchoredDraggable(state = draggableState, orientation = Orientation.Vertical)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            AnimatedVisibility(
                visible = place != null,
                enter = slideInVertically(),
                exit = slideOutVertically()
            ) {
                if (place != null) {
                    DetailBody(
                        place = place,
                        isOpen = isOpen,
                        modifier = Modifier
                            .fillMaxSize()
                            .offset { offset }
                    )
                }
            }
        }
    }
}

@Composable
fun DetailBody(
    place: PlaceBO,
    isOpen: Boolean,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Column(
        modifier = modifier
    ) {
        Column {
            val address = place.address
            val coordinates = place.coordinates
            if (!address.isNullOrBlank() && coordinates != null) {
                ActionButton(onCLick = {
                    val (latitude, longitude) = coordinates
                    val intent = IntentBuilder.mapsIntent(latitude, longitude, address)
                    context.startMapsOrPlayStore(intent)
                }, icon = Icons.Default.Place, text = address)
            }
            val phone = place.phone
            if (!phone.isNullOrBlank()) {
                ActionButton(onCLick = {
                    val intent = IntentBuilder.phoneIntent(phone)
                    context.startActivity(intent)
                }, icon = Icons.Default.Call, text = phone)
            }
            val webSite = place.webSite
            if (webSite != null) {
                ActionButton(onCLick = {
                    val intent = IntentBuilder.viewIntent(webSite)
                    context.startActivity(intent)
                }, icon = Icons.AutoMirrored.Filled.ExitToApp, text = webSite.toString())
            }
            Info(place = place, isOpen = isOpen)
        }
    }
}

@Composable
fun ActionButton(
    onCLick: () -> Unit,
    icon: ImageVector,
    text: String
) {
    RowItem(
        onCLick = onCLick
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier
                .padding(end = 10.dp)
        )
        Text(
            text = text
        )
    }
}

@Composable
fun Photo(
    place: PlaceBO,
    modifier: Modifier = Modifier,
    viewModel: PhotoViewModel = getViewModel { parametersOf(place) }
) {
    Image(
        painter = viewModel.photo?.let {
            BitmapPainter(image = it.asImageBitmap())
        } ?: run {
            painterResource(id = R.drawable.placeholder)
         },
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}

@Composable
fun Info(
    place: PlaceBO,
    isOpen: Boolean
) {
    ColumnItem {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp)
        ) {
            if (place.schedule.isNotEmpty()) {
                Text(
                    text = stringResource(if (isOpen) R.string.place_open else R.string.place_closed)
                )
            }
            val rating = place.rating
            if (rating != null) {
                Text(
                    text = stringResource(R.string.place_rating, rating)
                )
            }
            Text(
                text = stringResource(R.string.place_num_ratings, place.numRatings)
            )
        }
        place.schedule.forEach { schedule ->
            DynamicText(
                text = schedule.capitalize(Locale.current),
                modifier = Modifier.padding(vertical = 2.dp)
            )
        }
    }
}

@Composable
fun DynamicText(
    text: String,
    modifier: Modifier = Modifier
) {
    var fontSize by remember { mutableStateOf(16.sp) }
    var readyToDraw by remember { mutableStateOf(false) }

    Text(
        text = text,
        fontSize = fontSize,
        onTextLayout = { result ->
            if (result.lineCount > 1) {
                fontSize = fontSize.times(0.95)
            } else {
                readyToDraw = true
            }
        },
        modifier = modifier.drawWithContent {
            if (readyToDraw) {
                drawContent()
            }
        }
    )
}

@Preview
@Composable
fun DetailPreview() {
    ComposeTheme {
        Surface(color = MaterialTheme.colors.background) {
            DetailBody(
                place = PlaceBO(
                    id = "ChIJM2MMC_zzQQ0RMgmMC5ljRf0",
                    name = "Cafetería la Bruja Motera",
                    address = "C. Moratalaz, 12, 28977 Casarrubuelos, Madrid, Spain",
                    coordinates = CoordinatesBO(latitude=40.1688169, longitude=-3.831045700000001),
                    phone = "+34 647 80 96 01",
                    rating = 4.4,
                    numRatings = 335,
                    webSite = Uri.parse("https://es-es.facebook.com/pages/category/Tapas-Bar---Restaurant/La-Bruja-Motera-1784528441795885/"),
                    schedule = listOf("Monday: Closed", "Tuesday: 7:30 AM – 10:00 PM", "Wednesday: 7:30 AM – 10:00 PM", "Thursday: 7:30 AM – 10:00 PM", "Friday: 8:30 AM – 11:00 PM", "Saturday: 8:30 AM – 11:00 PM", "Sunday: 8:30 AM – 5:00 PM")
                ),
                isOpen = false
            )
        }
    }
}