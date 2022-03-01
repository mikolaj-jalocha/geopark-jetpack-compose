package com.geopark.feature_locations_events.presentation

import com.geopark.feature_locations_events.data.local.model.Location
import com.geopark.feature_locations_events.domain.util.LocationType
import com.geopark.feature_locations_events.domain.util.SortType

//State wrapper class

data class LocationsState(
    var locations : List<Location> = emptyList(),
    val locationType: LocationType = LocationType.All,
    val sortType: SortType = SortType.Default,
    val isLoading: Boolean = false
)
