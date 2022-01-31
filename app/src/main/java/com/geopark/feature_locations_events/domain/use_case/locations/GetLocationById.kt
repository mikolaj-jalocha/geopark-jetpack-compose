package com.geopark.feature_locations_events.domain.use_case.locations

import com.geopark.feature_locations_events.data.local.entity.LocationEntity
import com.geopark.feature_locations_events.data.local.model.Location
import com.geopark.feature_locations_events.domain.repository.LocationRepository

class GetLocationById(
    private val repository: LocationRepository
) {
    suspend operator fun invoke(
        locationId : String
    ) : Location {
        return repository.getLocationById(locationId) ?: Location()
    }
}