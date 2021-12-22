package com.geopark.feature_events.presentation.events_list

import java.time.Month

sealed class CalendarPanelEvent {
    data class ChangeDay(val newDayNumber : Int) : CalendarPanelEvent()
    data class ChangeMonth(val monthsToAdd : Int) : CalendarPanelEvent()
}
