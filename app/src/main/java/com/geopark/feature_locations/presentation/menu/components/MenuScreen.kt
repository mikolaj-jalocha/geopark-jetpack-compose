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
import coil.annotation.ExperimentalCoilApi
import com.geopark.feature_locations.presentation.LocationsEvent
import com.geopark.feature_locations.presentation.LocationsViewModel
import com.geopark.feature_locations.presentation.components.CategoriesSection
import com.geopark.feature_locations.presentation.menu.components.Tile
import com.geopark.feature_locations.presentation.menu.components.TileSectionTitle


@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
@Preview
fun MenuScreen(
    viewModel: LocationsViewModel = hiltViewModel()
) {



        val state = viewModel.state.value
        val scope = rememberCoroutineScope()
        Scaffold(
            //TODO: add top bar
        ) {

            Column(Modifier.fillMaxSize()) {

                CategoriesSection()

                TileSectionTitle(title = "Popular places", seeAllClick = {})

               LazyRow {
                    itemsIndexed(state.locations) { _, location ->
                        Tile(
                            modifier = Modifier.clickable {
                                //TODO :implement navigation
                                viewModel.onEvent(
                                    LocationsEvent.ChangeRecentlyWatched(
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
                                    LocationsEvent.ChangeFavorite(
                                        !location.isFavorite,
                                        location
                                    )
                                )
                            })
                    }
                }

                TileSectionTitle(title = "Recently watched", seeAllClick = {})

                LazyRow {
                    itemsIndexed(state.locations.filter { it.wasRecentlyWatched }) { _, location ->
                        Tile(
                            modifier = Modifier.clickable {
                                //TODO :implement navigation
                                viewModel.onEvent(
                                    LocationsEvent.ChangeRecentlyWatched(
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
                                    LocationsEvent.ChangeFavorite(
                                        !location.isFavorite,
                                        location
                                    )
                                )
                            })
                    }
                }
                TileSectionTitle(title = "Favorites", seeAllClick = {})

               LazyRow {
                    itemsIndexed(state.locations.filter { it.isFavorite }) { _, location ->
                        Tile(
                            modifier = Modifier.clickable {
                                //TODO implement navigation
                                viewModel.onEvent(
                                    LocationsEvent.ChangeRecentlyWatched(
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
                                    LocationsEvent.ChangeFavorite(
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

