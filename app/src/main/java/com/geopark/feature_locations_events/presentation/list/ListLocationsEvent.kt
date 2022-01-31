package com.geopark.feature_locations_events.presentation.list

import com.geopark.feature_locations_events.data.local.entity.LocationEntity
import com.geopark.feature_locations_events.data.local.model.Location
import com.geopark.feature_locations_events.domain.util.LocationOrder

sealed class ListLocationsEvent{
    data class ChangeFavorite(val newValue : Boolean, val location : Location) :  ListLocationsEvent()
    data class ChangeSearchQuery(val query : String) : ListLocationsEvent()
    data class ChangeRecentlyWatched(val newValue : Boolean, val location : Location) :  ListLocationsEvent()
    data class Order(val locationOrder: LocationOrder) : ListLocationsEvent()
}
