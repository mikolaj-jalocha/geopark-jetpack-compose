package com.geopark.feature_locations.presentation

import com.geopark.feature_locations.domain.model.Location
import com.geopark.feature_locations.domain.util.LocationType

sealed class LocationsEvent{
    data class Type(val locationType: LocationType) : LocationsEvent()
    data class ChangeFavorite(val newValue : Boolean, val location : Location) :  LocationsEvent()
    data class ChangeRecentlyWatched(val newValue : Boolean, val location : Location) :  LocationsEvent()

}

