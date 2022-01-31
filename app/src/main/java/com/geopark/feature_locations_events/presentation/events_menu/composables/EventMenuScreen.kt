package com.geopark.feature_locations_events.presentation.events_menu.composables

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.geopark.R
import com.geopark.feature_locations_events.domain.util.EventCategory
import com.geopark.feature_locations_events.presentation.UiEvent
import com.geopark.feature_locations_events.presentation.events_list.composables.CalendarPanel
import com.geopark.feature_locations_events.presentation.events_menu.EventsMenuEvent
import com.geopark.feature_locations_events.presentation.events_menu.EventsMenuViewModel
import com.geopark.feature_locations_events.presentation.menu.composables.Tile
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import kotlinx.coroutines.flow.collectLatest


@ExperimentalAnimationApi
@ExperimentalCoilApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
@Preview
fun EventMenuScreen(viewModel: EventsMenuViewModel = hiltViewModel()) {

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

    // TODO: Change int to enum
    val selectedChipIndex = remember {
        mutableStateOf(-1)
    }
    val selectedCategoryIndex = rememberSaveable { mutableStateOf(0) }
    Scaffold(scaffoldState = scaffoldState) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(1300.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Chip(text = "Category", isSelected = selectedChipIndex.value == 0) {
                            if (selectedChipIndex.value == 0) selectedChipIndex.value =
                                -1 else selectedChipIndex.value = 0
                        }
                        Chip(text = "Calendar", isSelected = selectedChipIndex.value == 1) {
                            if (selectedChipIndex.value == 1) selectedChipIndex.value =
                                -1 else selectedChipIndex.value = 1
                        }
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(painter = painterResource(id = R.drawable.ic_filter), contentDescription = "Filter",tint = colors.onBackground)
                        }
                    }
            }
            item {
                AnimatedContent(targetState = selectedChipIndex.value) { targetState ->
                    when (targetState) {
                        -1 -> {
                        }
                        0 -> {
                            TypeSelectionPanel(
                                EventCategory.values().map { it.categoryName },
                                selectedCategoryIndex.value
                            ) { categoryName, newIndex ->
                                selectedCategoryIndex.value = newIndex
                                viewModel.onEvent(
                                    EventsMenuEvent.ChangeCategory(
                                        EventCategory.values()
                                            .first { it.categoryName == categoryName }
                                    )
                                )
                            }
                        }
                        1 -> {
                            CalendarPanel(
                                state = viewModel.calendarState.value,
                                onDayChange = {},
                                onMonthChange = {}
                            )
                        }
                    }
                }
            }

            item {
                FlowRow(
                    mainAxisAlignment = FlowMainAxisAlignment.SpaceEvenly,
                    mainAxisSpacing = 8.dp,
                    crossAxisSpacing = 8.dp
                ) {
                    eventsState.events.distinctBy { it.event.eventTitle }.forEach { event ->
                        Tile(
                            photoPath = "",
                            name = event.event.eventTitle,
                            isWide = false,
                            isFavorite = false
                        ) {

                        }
                    }
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun TypeSelectionPanel(
    list: List<String>,
    selected: Int,
    onClick: (String, Int) -> Unit
) {
    FlowRow(
        mainAxisAlignment = FlowMainAxisAlignment.SpaceAround,
        modifier = Modifier.fillMaxWidth()
    ) {
        list.forEachIndexed { index, text ->
            Chip(text = text, isSelected = index == selected) {
                onClick(it, index)
            }
        }

    }
}
@ExperimentalMaterialApi
@Composable
fun Chip(
    text: String,
    isSelected: Boolean,
    shape: Shape = RoundedCornerShape(8.dp),
    onClick: (String) -> Unit
) {
    val colorState =
        animateColorAsState(if (isSelected) colors.background else colors.primary)
    Surface(
        onClick = { onClick(text) },
        shape = shape,
        color = colorState.value,
        elevation = 2.dp,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 2.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.subtitle1.copy(letterSpacing = 0.1.sp),
            modifier = Modifier.padding(4.dp)
        )
    }

}




