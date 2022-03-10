package com.geoparkcompose.ui.menu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.geopark.core.presentation.util.Screen
import com.geopark.feature_locations_events.presentation.UiEvent
import com.geopark.feature_locations_events.presentation.components.CategoriesSection
import com.geopark.feature_locations_events.presentation.menu.MenuLocationsEvent
import com.geopark.feature_locations_events.presentation.menu.MenuViewModel
import com.geopark.feature_locations_events.presentation.menu.composables.Tile
import com.geopark.feature_locations_events.presentation.menu.composables.TileTitleSeeAll
import kotlinx.coroutines.flow.collectLatest


@ExperimentalCoilApi
@Composable
fun MenuScreen(
    viewModel: MenuViewModel = hiltViewModel(),
    navigateTo: (String) -> Unit
) {
    val state = viewModel.state.value
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
    ) {

        LazyColumn(
            modifier =
            Modifier
                .fillMaxSize(), contentPadding = PaddingValues(bottom = 24.dp)
        ) {

            item {
                CategoriesSection(
                    locationType = state.locationType,
                    onLocationChange = { newLocationType ->
                        viewModel.onEvent(MenuLocationsEvent.Type(newLocationType))
                    })
            }
            item {
                TileTitleSeeAll(title = "Popular places", seeAllClick = {
                    navigateTo(
                        Screen.ListScreen.route + "/${state.locationType}"
                    )
                })
            }

            if (state.isLoading && state.locations.isEmpty()) {
                item {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(
                                CenterHorizontally
                            )
                        )
                    }
                }
            } else {
                item {
                    LazyRow {
                        itemsIndexed(state.locations) { _, location ->
                            Tile(
                                modifier = Modifier
                                    .clickable {
                                        navigateTo(Screen.ContentScreen.route + "/${location.location.locationId}")
                                        viewModel.onEvent(
                                            MenuLocationsEvent.ChangeRecentlyWatched(
                                                true,
                                                location
                                            )
                                        )
                                    }
                                    .padding(start = 16.dp),
                                isWide = false,
                                photoPath = location.photos[0].url,
                                name = location.location.name,
                                isFavorite = false,
                                onFavoriteClick = {

                                })
                        }
                    }
                }
            }

        }

    }
}



