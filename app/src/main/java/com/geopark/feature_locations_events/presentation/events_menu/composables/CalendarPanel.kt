package com.geopark.feature_locations_events.presentation.events_list.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.geopark.core.util.getShortName
import com.geopark.feature_locations_events.presentation.events_menu.calendar.CalendarPanelState
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun CalendarPanel(
    state: CalendarPanelState,
    onDayChange: (Int) -> Unit,
    onMonthChange: (Int) -> Unit,
) {
    Surface(elevation = 1.dp) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        ) {
            val dayRowState = rememberLazyListState(state.currentDay-1)
            val coroutineScope = rememberCoroutineScope()

            val currentDate = LocalDate.now()

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                    IconButton(
                       enabled = !(LocalDate.now().month == state.month && LocalDate.now().year == state.year),
                        onClick = {
                            onMonthChange(-1)
                            coroutineScope.launch {
                                dayRowState.animateScrollToItem(if (state.month.minus(1) == currentDate.month && state.year == currentDate.year) currentDate.dayOfMonth-1 else 0)
                            }
                        },
                        modifier = Modifier.semantics {
                            contentDescription = "Previous month"
                        },
                    ) {
                        Icon(Icons.Filled.ArrowBack, "Previous month")
                    }

                Text("${state.month.name}, ${state.year}", style = MaterialTheme.typography.h6, modifier = Modifier.semantics { contentDescription = "Selected month" })
                IconButton(onClick = {
                    onMonthChange(1)
                    coroutineScope.launch {
                        dayRowState.animateScrollToItem(if (state.month.plus(1) == LocalDate.now().month && state.year == LocalDate.now().year) LocalDate.now().dayOfMonth-1 else 0)
                    }
                }, modifier = Modifier.semantics { contentDescription = "Next month" }) {
                    Icon(Icons.Filled.ArrowForward, "Next month")
                }
            }

            LazyRow(
                modifier = Modifier.fillMaxWidth().semantics { contentDescription = "Select day" },
                contentPadding = PaddingValues(horizontal = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                state = dayRowState
            ) {

                items(state.daysInMonth) { i ->
                    CalendarDayItem(
                        modifier = Modifier.semantics { contentDescription =  "Day number" },
                        dayName = LocalDate.of(
                            state.year,
                            state.month,
                            i + 1
                        ).dayOfWeek.getShortName(), //or get name here
                        dayNumber = i + 1,
                        isSelected = (state.selectedDayOfMonth == i + 1 && state.year == currentDate.year)
                    ) {
                        onDayChange(i + 1)
                    }
                }
            }
        }
    }
}

@Composable
fun CalendarDayItem(modifier : Modifier = Modifier,dayName: String, dayNumber: Int, isSelected: Boolean, onSelect: () -> Unit) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(50.dp))
            .height(80.dp)
            .width(45.dp)
            .clickable(role = Role.Tab) {
                onSelect()
            }
            .semantics { selected = isSelected }
            .background(if (isSelected) MaterialTheme.colors.primary else Color.Transparent),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Surface(
            modifier = Modifier.size(32.dp),
            elevation = 8.dp,
            color = MaterialTheme.colors.surface,
            shape = CircleShape
        ) {
            Text(
                dayNumber.toString(),
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        Text(
            dayName,
            color = if (isSelected) MaterialTheme.colors.surface else MaterialTheme.colors.onSurface,
            style = MaterialTheme.typography.overline.copy(fontWeight = FontWeight.Medium),
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}





