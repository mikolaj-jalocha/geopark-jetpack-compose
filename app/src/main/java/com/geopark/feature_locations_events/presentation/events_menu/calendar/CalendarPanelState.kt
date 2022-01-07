package com.geopark.feature_locations_events.presentation.events_menu.calendar

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth

data class CalendarPanelState(
    val year: Int = YearMonth.now().year,
    val month: Month = YearMonth.now().month,
    val daysInMonth: Int = YearMonth.of(year, month).lengthOfMonth(),
    val selectedDayOfMonth : Int? = if(LocalDate.now().month != month) null else LocalDate.now().dayOfMonth,
   //util variables
    val day: DayOfWeek? = selectedDayOfMonth?.let { LocalDate.of(year, month, it).dayOfWeek },
    val currentDay : Int = if(LocalDate.now().month != month || LocalDate.now().year != year) 1 else LocalDate.now().dayOfMonth,
    val numberOfDays : Int  = if(LocalDate.now().month != month) daysInMonth else  daysInMonth - currentDay +1
)