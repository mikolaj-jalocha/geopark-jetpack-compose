package com.geopark.feature_locations.presentation.list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.geopark.feature_locations.domain.util.LocationOrder
import com.geopark.feature_locations.presentation.components.TileTitleSortBy
import com.geopark.feature_locations.presentation.list.ListLocationsEvent
import com.geopark.feature_locations.presentation.list.ListViewModel
import com.geopark.feature_locations.presentation.menu.composables.Tile
import com.geopark.feature_locations.presentation.util.Screen

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun ListScreen(
    viewModel: ListViewModel = hiltViewModel(),
    navigateTo: (String) -> Unit,
    navigateUp: () -> Unit
) {

    val state = viewModel.state.value
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = { ListTopBar(navigateUp,{viewModel.onEvent(ListLocationsEvent.ChangeSearchQuery(it))})}
    ) {

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment  = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(bottom = 24.dp),
        ) {
            item {
                TileTitleSortBy(
                    modifier = Modifier.padding(
                        start = 12.dp,
                        top = 16.dp,
                    ), state.locationType.toString()
                ) {
                    viewModel.onEvent(
                        ListLocationsEvent.Order(LocationOrder.Name(it)),
                    )
                }
            }
            itemsIndexed(state.locations,) { _, location ->
                Tile(
                    modifier = Modifier.clickable {
                        navigateTo(Screen.ContentScreen.route + "/${location.name}")
                        viewModel.onEvent(
                            ListLocationsEvent.ChangeRecentlyWatched(
                                true,
                                location
                            )
                        )
                    },
                    photoPath = location.photo,
                    name = location.name,
                    isWide = true,
                    isFavorite = location.isFavorite
                ) {
                    viewModel.onEvent(
                        ListLocationsEvent.ChangeFavorite(
                            newValue = !location.isFavorite,
                            location
                        )
                    )
                }
            }


        }
    }

}



