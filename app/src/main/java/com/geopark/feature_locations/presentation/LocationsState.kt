package com.geopark.feature_locations.presentation

import com.geopark.feature_locations.domain.model.Location
import com.geopark.feature_locations.domain.util.LocationType

//State wrapper class

data class LocationsState(
    var locations : List<Location> = emptyList(),
    val locationType : LocationType = LocationType.All
)
