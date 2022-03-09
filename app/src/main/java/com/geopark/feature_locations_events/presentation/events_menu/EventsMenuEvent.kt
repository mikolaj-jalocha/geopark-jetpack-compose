package com.geopark.feature_locations_events.presentation.events_menu

import com.geopark.feature_locations_events.domain.util.EventCategory

sealed class EventsMenuEvent {
    data class ChangeCategory(val category: EventCategory) : EventsMenuEvent()
    data class ChangeDay(val newDayNumber: Int) : EventsMenuEvent()
    data class ChangeMonth(val monthsToAdd: Int) : EventsMenuEvent()
    object  GetUpcomingEvents : EventsMenuEvent()
}