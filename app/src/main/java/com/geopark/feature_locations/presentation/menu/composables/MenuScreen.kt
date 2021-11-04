package com.geoparkcompose.ui.menu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.geopark.feature_locations.presentation.menu.MenuLocationsEvent
import com.geopark.feature_locations.presentation.menu.MenuViewModel
import com.geopark.feature_locations.presentation.components.CategoriesSection
import com.geopark.feature_locations.presentation.menu.composables.Tile
import com.geopark.feature_locations.presentation.menu.composables.TileTitleSeeAll
import com.geopark.feature_locations.presentation.util.Screen


@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun MenuScreen(
    navController: NavController,
    viewModel: MenuViewModel = hiltViewModel()
) {
        val state = viewModel.state.value
        val scope = rememberCoroutineScope()
        Scaffold(
            //TODO: add top bar
        ) {

            Column(Modifier.fillMaxSize()) {

                CategoriesSection(locationType = state.locationType, onLocationChange = { newLocationType ->
                    viewModel.onEvent(MenuLocationsEvent.Type(newLocationType))
                })

                TileTitleSeeAll(title = "Popular places", seeAllClick = { navController.navigate(
                    Screen.ListScreen.route +"/${state.locationType}"
                )})

               LazyRow {
                    itemsIndexed(state.locations) { _, location ->
                        Tile(
                            modifier = Modifier.clickable {
                                navController.navigate(Screen.ContentScreen.route+"/${location.name}")
                                viewModel.onEvent(
                                    MenuLocationsEvent.ChangeRecentlyWatched(
                                        true,
                                        location
                                    )
                                )
                            },
                            isWide = false,
                            photoPath = location.photo,
                            name = location.name,
                            isFavorite = location.isFavorite,
                            onFavoriteClick = {
                                viewModel.onEvent(
                                    MenuLocationsEvent.ChangeFavorite(
                                        !location.isFavorite,
                                        location
                                    )
                                )
                            })
                    }
                }

                TileTitleSeeAll(title = "Recently watched", seeAllClick = { /* TODO: implement list of all screens for recently watched */})

                LazyRow {
                    itemsIndexed(state.locations.filter { it.wasRecentlyWatched }) { _, location ->
                        Tile(
                            modifier = Modifier.clickable {
                                //TODO :implement navigation
                                viewModel.onEvent(
                                    MenuLocationsEvent.ChangeRecentlyWatched(
                                        true,
                                        location
                                    )
                                )
                            },
                            photoPath = location.photo,
                            isWide = false,
                            name = location.name,
                            isFavorite = location.isFavorite,
                            onFavoriteClick = {
                                viewModel.onEvent(
                                    MenuLocationsEvent.ChangeFavorite(
                                        !location.isFavorite,
                                        location
                                    )
                                )
                            })
                    }
                }
                TileTitleSeeAll(title = "Favorites", seeAllClick = { /* TODO: implement list of all screens for favorites */})

               LazyRow {
                    itemsIndexed(state.locations.filter { it.isFavorite }) { _, location ->
                        Tile(
                            modifier = Modifier.clickable {
                                //TODO implement navigation
                                viewModel.onEvent(
                                    MenuLocationsEvent.ChangeRecentlyWatched(
                                        true,
                                        location
                                    )
                                )
                            },
                            photoPath = location.photo,
                            isWide = false,
                            name = location.name,
                            isFavorite = location.isFavorite,
                            onFavoriteClick = {
                                viewModel.onEvent(
                                    MenuLocationsEvent.ChangeFavorite(
                                        !location.isFavorite,
                                        location
                                    )
                                )
                            })
                    }
                }
            }
        }
}

