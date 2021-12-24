package com.geopark.feature_locations_events.presentation

import com.geopark.feature_locations_events.domain.model.Location
import com.geopark.feature_locations_events.domain.util.LocationOrder
import com.geopark.feature_locations_events.domain.util.LocationType
import com.geopark.feature_locations_events.domain.util.OrderType

//State wrapper class

data class LocationsState(
    var locations : List<Location> = emptyList(),
    val locationType : LocationType = LocationType.All,
    val locationOrder : LocationOrder = LocationOrder.Name(OrderType.Default),
    val isLoading: Boolean = false
)
