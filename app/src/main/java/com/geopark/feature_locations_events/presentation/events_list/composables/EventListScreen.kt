package com.geopark.feature_locations_events.presentation.events_list.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.geopark.feature_locations_events.presentation.UiEvent
import com.geopark.feature_locations_events.presentation.events_list.CalendarPanelEvent
import com.geopark.feature_locations_events.presentation.events_list.EventScreenViewModel
import com.geopark.feature_locations_events.presentation.menu.composables.Tile
import kotlinx.coroutines.flow.collectLatest

const val TILE_HEIGHT = 180
const val TILE_WIDTH = 360


@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable

fun EventListScreen(
    viewModel: EventScreenViewModel = hiltViewModel(),
    navigateTo: (String) -> Unit
) {

    val calendarPanelState = viewModel.calendarState.value
    val eventsState = viewModel.eventsState.value
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

    Scaffold(scaffoldState = scaffoldState) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(bottom = 24.dp),
        ) {
            item {
                CalendarPanel(
                    data = calendarPanelState,
                    onDayChange = { viewModel.onCalendarEvent(CalendarPanelEvent.ChangeDay(it)) },
                    onMonthChange = { viewModel.onCalendarEvent(CalendarPanelEvent.ChangeMonth(it)) })
            }
            if (eventsState.isLoading && eventsState.events.isEmpty()) {
                item {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                    }
                }
            } else {
                this@LazyColumn.itemsIndexed(eventsState.events) { _, event ->
                    Tile(
                        photoPath = event.photoPath,
                        name = event.title,
                        isWide = true,
                        isFavorite = false
                    ) {

                    }
                }
            }
        }
    }

}










