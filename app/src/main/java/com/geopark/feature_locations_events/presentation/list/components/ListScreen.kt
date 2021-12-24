package com.geopark.feature_locations_events.presentation.list.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.geopark.feature_locations_events.domain.util.LocationOrder
import com.geopark.feature_locations_events.presentation.UiEvent
import com.geopark.feature_locations_events.presentation.components.TileTitleSortBy
import com.geopark.feature_locations_events.presentation.list.ListLocationsEvent
import com.geopark.feature_locations_events.presentation.list.ListViewModel
import com.geopark.feature_locations_events.presentation.menu.composables.Tile
import com.geopark.core.presentation.util.Screen
import kotlinx.coroutines.flow.collectLatest

@ExperimentalAnimationApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun ListScreen(
    viewModel: ListViewModel = hiltViewModel(),
    navigateTo: (String) -> Unit,
    navigateUp: () -> Unit
) {

    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    var isSearchBarEnabled by rememberSaveable {
        mutableStateOf(false)
    }

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
        topBar = {
            ListTopBar(
                text = viewModel.searchQuery.value,
                isSearchEnabled = isSearchBarEnabled,
                onSearchBarClick = { isSearchBarEnabled = it },
                onValueChange = { viewModel.onEvent(ListLocationsEvent.ChangeSearchQuery(it)) },
                onNavigateUp = navigateUp
            )
        }
    ) {

        Surface(onClick = { isSearchBarEnabled = false }, indication = null) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(bottom = 24.dp),
            ) {
                item {
                    TileTitleSortBy(
                        modifier = Modifier
                            .padding(
                                start = 12.dp,
                                top = 16.dp,
                            ),
                        state.locationType.toString()
                    ) {
                        viewModel.onEvent(
                            ListLocationsEvent.Order(LocationOrder.Name(it)),
                        )
                    }
                    if (state.isLoading && state.locations.isEmpty()) {
                        Column(modifier = Modifier.fillMaxSize()) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                        }
                    } else {
                        this@LazyColumn.itemsIndexed(state.locations) { _, location ->
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
                                isFavorite = false
                            ) {

                            }
                        }
                    }
                }
            }
        }
    }
}








