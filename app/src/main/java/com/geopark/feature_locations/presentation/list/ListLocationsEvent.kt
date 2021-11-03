package com.geopark.feature_locations.presentation.list

import com.geopark.feature_locations.domain.model.Location
import com.geopark.feature_locations.domain.util.LocationType
import com.geopark.feature_locations.presentation.menu.MenuLocationsEvent

sealed class ListLocationsEvent{
    data class ChangeFavorite(val newValue : Boolean, val location : Location) :  ListLocationsEvent()
}
