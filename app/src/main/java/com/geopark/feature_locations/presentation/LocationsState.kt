package com.geopark.feature_locations.presentation

import com.geopark.feature_locations.domain.model.Location
import com.geopark.feature_locations.domain.util.LocationOrder
import com.geopark.feature_locations.domain.util.LocationType
import com.geopark.feature_locations.domain.util.OrderType

//State wrapper class

data class LocationsState(
    var locations : List<Location> = emptyList(),
    val locationType : LocationType = LocationType.All,
    val locationOrder : LocationOrder = LocationOrder.Name(OrderType.Default)
)
