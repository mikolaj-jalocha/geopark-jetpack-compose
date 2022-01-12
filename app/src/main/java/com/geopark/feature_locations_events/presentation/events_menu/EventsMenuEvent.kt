package com.geopark.feature_locations_events.presentation.events_menu

import com.geopark.feature_locations_events.domain.util.EventCategory

sealed class EventsMenuEvent {

    data class ChangeCategory(val category : EventCategory) : EventsMenuEvent()



}