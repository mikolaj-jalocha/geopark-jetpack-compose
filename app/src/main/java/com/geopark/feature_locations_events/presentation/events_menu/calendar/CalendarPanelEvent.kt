package com.geopark.feature_locations_events.presentation.events_menu.calendar

sealed class CalendarPanelEvent {
    data class ChangeDay(val newDayNumber : Int) : CalendarPanelEvent()
    data class ChangeMonth(val monthsToAdd : Int) : CalendarPanelEvent()
}