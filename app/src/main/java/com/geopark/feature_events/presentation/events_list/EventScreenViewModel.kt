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


    fun onCalendarEvent(calendarEvent: CalendarPanelEvent) {
        when (calendarEvent) {
            is CalendarPanelEvent.ChangeDay -> {


                _state.value = _state.value.copy(
                    selectedDayOfMonth = calendarEvent.newDayNumber
                )
            }
            is CalendarPanelEvent.ChangeMonth -> {

                val newYear = YearMonth.of(state.value.year, state.value.month).plusMonths(calendarEvent.monthsToAdd.toLong())
                val newMonth = newYear.month
                    _state.value = CalendarPanelState(newYear.year,newMonth, )
  
            }
        }
    }

}


data class CalendarPanelState(
    val year: Int = YearMonth.now().year,
    val month: Month = YearMonth.now().month,
    val daysInMonth: Int = YearMonth.of(year, month).lengthOfMonth(),
    val selectedDayOfMonth : Int? = if(LocalDate.now().month != month) null else LocalDate.now().dayOfMonth,
   //util variables
    val day: DayOfWeek? = selectedDayOfMonth?.let { LocalDate.of(year,month, it).dayOfWeek },
    val currentDay : Int = if(LocalDate.now().month != month || LocalDate.now().year != year) 1 else LocalDate.now().dayOfMonth,
    val numberOfDays : Int  = if(LocalDate.now().month != month) daysInMonth else  daysInMonth - currentDay +1
)





