package com.geopark.feature_locations.domain.use_case

import com.geopark.feature_locations.domain.model.Location
import com.geopark.feature_locations.domain.repository.LocationRepository

class GetLocationByName(
    private val repository: LocationRepository
) {

    suspend operator fun invoke(
        locationName : String
    ) : Location {
        return repository.getLocationByName(locationName) ?: Location()
    }

}