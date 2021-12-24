package com.geopark.feature_locations_events.presentation.events_list

import com.geopark.feature_locations_events.domain.model.Event

data class EventsState(
    val events : List<Event> = emptyList(),
    val isLoading : Boolean = false
)
