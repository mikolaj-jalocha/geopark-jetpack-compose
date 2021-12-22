package com.geopark.feature_events.presentation.events_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EventScreenViewModel @Inject constructor(
    //future use case here
) : ViewModel() {

    private val _state = mutableStateOf(CalendarPanelState())
    val state: State<CalendarPanelState> = _state

}


data class CalendarPanelState(
    val year: Int = YearMonth.now().year,
    val month: Month = YearMonth.now().month,
    val day: DayOfWeek = LocalDate.now().dayOfWeek,
    val daysInMonth: Int = YearMonth.of(year, YearMonth.now().month).lengthOfMonth(),
)