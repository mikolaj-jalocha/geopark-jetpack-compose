package com.geopark.feature_locations_events.domain.use_case.locations

import com.geopark.feature_locations_events.domain.model.Location
import com.geopark.feature_locations_events.domain.repository.LocationRepository

class GetLocationByName(
    private val repository: LocationRepository
) {

    suspend operator fun invoke(
        locationName : String
    ) : Location {
        return repository.getLocationByName(locationName) ?: Location()
    }

}