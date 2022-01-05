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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.geopark.feature_locations_events.presentation.UiEvent
import com.geopark.feature_locations_events.presentation.events_menu.EventsMenuViewModel
import com.geopark.feature_locations_events.presentation.events_list.composables.CalendarPanel
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
fun EventMenu(viewModel: EventsMenuViewModel = hiltViewModel()) {


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

    Scaffold(scaffoldState = scaffoldState) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(1300.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            // TODO: Change int to enum
            item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Chip(text = "Category", isSelected = selectedChipIndex.value == 0) {
                            if (selectedChipIndex.value == 0) selectedChipIndex.value =
                                -1 else selectedChipIndex.value = 0
                        }
                        Chip(text = "Location", isSelected = selectedChipIndex.value == 1) {
                            if (selectedChipIndex.value == 1) selectedChipIndex.value =
                                -1 else selectedChipIndex.value = 1
                        }
                        Chip(text = "Date", isSelected = selectedChipIndex.value == 2) {
                            if (selectedChipIndex.value == 2) {
                                selectedChipIndex.value =
                                    -1
                            } else selectedChipIndex.value = 2
                        }

                }
            }
            item {
                AnimatedContent(targetState = selectedChipIndex.value) { targetState ->
                    when (targetState) {
                        -1 -> {}
                        0 -> TypeSelectionPanel()
                        1 -> {
                            var expanded by remember { mutableStateOf(true) }
                            LocationSelectionPanel()
                        }
                        2 -> {
                            CalendarPanel(
                                data = viewModel.calendarState.value,
                                onDayChange = {

                                },
                                onMonthChange = {

                                }
                            )
                        }
                    }
                }
            }

            // content depending on selected state
            item {

                    FlowRow(
                        mainAxisAlignment = FlowMainAxisAlignment.SpaceEvenly,
                        mainAxisSpacing = 8.dp,
                        crossAxisSpacing = 8.dp
                    ) {
                        eventsState.events.distinctBy { it.title }.forEach { event ->
                            Tile(
                                photoPath = event.photoPath,
                                name = event.title,
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
fun LocationSelectionPanel(
    locations: List<String> = listOf(
        "All locations",
        "Sudecka Zagroda Edukacyjna",
        "Galeria Pod Aniołem"
    )
) {
    var selectedLocationText by remember { mutableStateOf(locations[0]) }


        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth()
        ) {
            var expanded by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = {
                expanded = !expanded
            }) {
                OutlinedTextField(
                    readOnly = true,
                    value = selectedLocationText,
                    shape = RoundedCornerShape(8.dp),
                    onValueChange = { selectedLocationText = it },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded
                        )
                    },
                    label = { Text("Location") },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        backgroundColor = colors.background,
                    )
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {
                    locations.forEachIndexed { index, selectionLocation ->
                        DropdownMenuItem(
                            onClick = {
                                selectedLocationText = selectionLocation
                                expanded = false


                            }
                        ) {
                            Text(
                                text = selectionLocation,
                                color = if (selectedLocationText == selectionLocation) colors.primary else Color.Unspecified
                            )
                        }
                    }
                }


        }
    }
}


@ExperimentalMaterialApi
@Composable
fun TypeSelectionPanel(
    list: List<String> = listOf(
        "Wszystko",
        "Dla rodzin",
        "Dla dorosłych",
        "Historia",
        "Geologia i Minerały",
        "Kulinaria",
        "Paszport Odkrywcy",
        "Przyrodnicze",
        "Pod Dachem",
        "Rzemiosło i sztuka"
    )
) {

    val selected = remember {
        mutableStateOf(0)
    }

        FlowRow(
            mainAxisAlignment = FlowMainAxisAlignment.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            list.forEachIndexed { index, text ->
                Chip(text = text, isSelected = index == selected.value) {
                    selected.value = index
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
    onClick: () -> Unit
) {

    val colorState =
        animateColorAsState(if (isSelected) MaterialTheme.colors.background else MaterialTheme.colors.primary)
    Surface(
        onClick = onClick,
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




