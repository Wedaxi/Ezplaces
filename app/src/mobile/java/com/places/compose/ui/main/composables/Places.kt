package com.places.compose.ui.main.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.DrawerValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.places.compose.R
import com.places.compose.callbacks.MainEventsListener
import com.places.compose.callbacks.ShowAdListener
import com.places.compose.data.model.CoordinatesBO
import com.places.compose.data.model.PlaceBO
import com.places.compose.goToDetail
import com.places.compose.ui.main.viewmodel.MainViewModel
import com.places.compose.ui.main.viewmodel.PreferencesViewModel
import com.places.compose.ui.theme.ComposeTheme
import com.places.compose.util.IntentBuilder
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun LoadPlaces(
    preferencesViewModel: PreferencesViewModel,
    listener: MainEventsListener,
    navController: NavHostController,
    viewModel: MainViewModel
) {
    Tabs(
        preferencesViewModel = preferencesViewModel,
        listener = listener,
        navController = navController,
        viewModel = viewModel
    )
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun Tabs(
    preferencesViewModel: PreferencesViewModel,
    listener: MainEventsListener,
    navController: NavHostController,
    viewModel: MainViewModel
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(initialPage = preferencesViewModel.selectedHome.ordinal, pageCount = { 2 })
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scaffoldState = rememberScaffoldState(drawerState = drawerState)
    Scaffold(
        scaffoldState = scaffoldState,
        drawerShape = MaterialTheme.shapes.large,
        drawerContent = {
            Menu(
                preferencesViewModel = preferencesViewModel,
                navController = navController
            )
        },
        topBar = {
            Column {
                TopAppBar(
                    elevation = 0.dp,
                    title = {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = null
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            listener.showAutoCompleteActivity()
                        }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null
                            )
                        }
                    }
                )
                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage])
                        )
                    }
                ) {
                    Tab(
                        selected = pagerState.currentPage == 0,
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Place,
                                contentDescription = null
                            )
                        },
                        onClick = { scope.launch { pagerState.animateScrollToPage(0) } }
                    )
                    Tab(
                        selected = pagerState.currentPage == 1,
                        icon = {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = null
                            )
                        },
                        onClick = { scope.launch { pagerState.animateScrollToPage(1) } }
                    )
                }
            }
        },
        bottomBar = {
            AdsBanner()
        }
    ) { innerPadding ->
        ShowSnack(
            snackbarHostState = scaffoldState.snackbarHostState,
            show = !viewModel.locationAvailable,
            message = stringResource(id = R.string.snack_location_not_available),
            actionLabel = stringResource(id = R.string.snack_enable_location)
        ) {
            val intent = IntentBuilder.locationIntent()
            context.startActivity(intent)
        }
        HorizontalPager(
            state = pagerState
        ) { page ->
            Places(
                innerPadding = innerPadding,
                listener = listener,
                navController = navController,
                places = if (page == 0) viewModel.places else viewModel.favorites,
                refreshing = viewModel.isRefreshing,
                onRefresh = { viewModel.refresh(page) },
                coordinates = viewModel.lastLocation
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun Places(
    innerPadding: PaddingValues,
    listener: ShowAdListener,
    navController: NavHostController,
    places: List<PlaceBO>?,
    refreshing: Boolean,
    onRefresh: () -> Unit,
    coordinates: CoordinatesBO? = null
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = onRefresh
    )
    Box(
        modifier = Modifier.fillMaxSize().pullRefresh(pullRefreshState)
    ) {
        // hack to enable pull refresh when it's empty
        if (places.isNullOrEmpty()) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {}
        }
        AnimatedVisibility(
            visible = !places.isNullOrEmpty(),
            enter = slideInVertically(),
            exit = slideOutVertically()
        ) {
            LazyColumn(
                contentPadding = innerPadding,
                modifier = Modifier.fillMaxSize()
            ) {
                items(items = places.orEmpty(), key = { it.id }) { item ->
                    Place(
                        listener = listener,
                        navController = navController,
                        place = item,
                        location = coordinates
                    )
                }
            }
        }
        PullRefreshIndicator(
            refreshing = refreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
fun Place(
    listener: ShowAdListener,
    navController: NavHostController,
    place: PlaceBO,
    location: CoordinatesBO?
) {
    val (id, name, _, coordinates) = place
    RowItem(
        onCLick =  {
            listener.showRewardedAd {
                navController.goToDetail(id)
            }
        }
    ) {
        Icon(
            place = place,
            modifier = Modifier
                .padding(end = 10.dp)
                .size(40.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = name
            )
            if (location != null && coordinates != null) {
                val distance = location.calculateDistance(coordinates)
                Text(
                    text = stringResource(R.string.distance_meters, distance)
                )
            }
        }
    }
}

@Composable
fun Icon(place: PlaceBO, modifier: Modifier = Modifier) {
    val color = place.iconBackground?.let { Color(it) }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(color = color ?: Color.Transparent, shape = CircleShape)
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = place.icon),
            contentDescription = null,
            modifier = Modifier
                .size(25.dp)
        )
    }
}

@Preview
@Composable
fun PlacePreview() {
    ComposeTheme {
        Surface(color = MaterialTheme.colors.background) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                val navController = rememberNavController()
                Place(
                    navController = navController,
                    listener = { },
                    place = PlaceBO(id="ChIJM2MMC_zzQQ0RMgmMC5ljRf0", name="Cafetería la Bruja Motera", address="C. Moratalaz, 12, 28977 Casarrubuelos, Madrid, Spain", coordinates=null, icon="https://maps.gstatic.com/mapfiles/place_api/icons/v2/cafe_pinlet.png", iconBackground=-24985),
                    location = null
                )
                Place(
                    navController = navController,
                    listener = { },
                    place = PlaceBO(id="ChIJlwiVmvvzQQ0RXBRttThY9Oc", name="COVIRAN", address="C. de Miguel Hernández, 36, 28977 Casarrubuelos, Madrid, Spain", coordinates=null, icon="https://maps.gstatic.com/mapfiles/place_api/icons/v2/shoppingcart_pinlet.png", iconBackground=-11823373),
                    location = null
                )
            }
        }
    }
}