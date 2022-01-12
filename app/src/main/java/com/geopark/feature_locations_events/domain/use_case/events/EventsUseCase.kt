package com.geopark.feature_locations_events.domain.use_case.events

data class EventsUseCase(
    val getAllEvents: GetAllEvents,
    val getAllEventsDistinct: GetAllEventsDistinct
)