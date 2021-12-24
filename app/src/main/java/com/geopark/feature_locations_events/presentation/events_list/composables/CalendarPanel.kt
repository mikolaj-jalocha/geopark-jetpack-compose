package com.geopark.feature_locations_events.presentation.events_list.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.geopark.core.util.getShortName
import com.geopark.feature_locations_events.presentation.events_list.CalendarPanelState

import com.geopark.ui.theme.DirtyWhite
import com.geopark.ui.theme.NavyBlue
import java.time.LocalDate

@Composable
fun CalendarPanel(
    data: CalendarPanelState,
    onDayChange: (Int) -> Unit,
    onMonthChange: (Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(DirtyWhite)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
                IconButton(onClick = {
                    onMonthChange(-1)
                }) {
                    Icon(Icons.Filled.ArrowBack, "Previous month")
                }


            Text("${data.month.name}, ${data.year}", style = MaterialTheme.typography.h6)
            IconButton(onClick = {
                onMonthChange(1)
            }) {
                Icon(Icons.Filled.ArrowForward, "Next month")
            }
        }
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            items(data.numberOfDays) { i ->
                CalendarDayItem(
                    dayName = LocalDate.of(data.year, data.month, data.currentDay + i).dayOfWeek.getShortName(), //or get name here
                    dayNumber = data.currentDay + i,
                    isSelected = data.selectedDayOfMonth == i + data.currentDay
                ) {
                    onDayChange(data.currentDay + i)
                }
            }
        }
    }
}


@Composable
fun CalendarDayItem(dayName: String, dayNumber: Int, isSelected: Boolean, onSelect: () -> Unit) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(50.dp))
            .height(80.dp)
            .width(45.dp)
            .clickable {
                onSelect()
            }
            .background(if (isSelected) Color.Black else Color.Transparent),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Surface(
            modifier = Modifier.size(32.dp),
            elevation = 8.dp,
            color = Color.White,
            shape = CircleShape
        ) {
            Text(
                dayNumber.toString(),
                color = Color.Black,
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        Text(
            dayName,
            color = if (isSelected) Color.White else NavyBlue,
            style = MaterialTheme.typography.overline,
            modifier = Modifier.padding(bottom = 8.dp)
        )
    }
}




