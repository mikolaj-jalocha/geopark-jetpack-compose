package com.geopark.feature_locations_events.domain.use_case.locations

import com.geopark.feature_locations_events.domain.model.Location
import com.geopark.feature_locations_events.domain.repository.LocationRepository

class ChangeLocationData(
    private val repository: LocationRepository
) {

    suspend  operator fun invoke(location: Location) {
        repository.updateLocation(location)
    }
}