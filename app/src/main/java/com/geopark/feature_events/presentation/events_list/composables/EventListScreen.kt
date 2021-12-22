package com.geopark.feature_events.presentation.events_list.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.geopark.feature_events.presentation.events_list.EventScreenViewModel

const val TILE_HEIGHT = 180
const val TILE_WIDTH = 360


@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable

fun EventListScreen(
    viewModel: EventScreenViewModel = hiltViewModel(),
    navigateTo: (String) -> Unit
) {

    val state = viewModel.state.value

    Scaffold {
        Column {
            CalendarPanel(state,onDayChange = {}, onMonthChange = {})


        }
    }

}









