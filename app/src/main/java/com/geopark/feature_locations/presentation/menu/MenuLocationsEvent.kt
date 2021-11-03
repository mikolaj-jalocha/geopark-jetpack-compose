package com.geopark.feature_locations.presentation.menu

import com.geopark.feature_locations.domain.model.Location
import com.geopark.feature_locations.domain.util.LocationType

sealed class MenuLocationsEvent{
    data class Type(val locationType: LocationType) : MenuLocationsEvent()
    data class ChangeFavorite(val newValue : Boolean, val location : Location) :  MenuLocationsEvent()
    data class ChangeRecentlyWatched(val newValue : Boolean, val location : Location) :  MenuLocationsEvent()

}

