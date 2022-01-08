package com.geopark.feature_locations_events.presentation.events_menu

import com.geopark.feature_locations_events.domain.model.Event
import com.geopark.feature_locations_events.domain.util.EventCategory

data class EventsState(
    val events : List<Event> = emptyList(),
    val eventsLocations : List<String> = emptyList(),
    val category : EventCategory =  EventCategory.ALL,
    val isLoading : Boolean = false
)
