package com.geopark.feature_locations_events.presentation.menu

import com.geopark.feature_locations_events.data.local.entity.LocationEntity
import com.geopark.feature_locations_events.data.local.model.Location
import com.geopark.feature_locations_events.domain.util.LocationType

sealed class MenuLocationsEvent{
    data class Type(val locationType: LocationType) : MenuLocationsEvent()
    data class ChangeFavorite(val newValue : Boolean, val location : Location) :  MenuLocationsEvent()
    data class ChangeRecentlyWatched(val newValue : Boolean, val location : Location) :  MenuLocationsEvent()
}

