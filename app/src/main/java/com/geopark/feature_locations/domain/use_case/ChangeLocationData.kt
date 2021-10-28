package com.geopark.feature_locations.domain.use_case

import com.geopark.feature_locations.domain.model.Location
import com.geopark.feature_locations.domain.repository.LocationRepository

class ChangeLocationData(
    private val repository: LocationRepository
) {

    suspend operator fun invoke(location: Location) {
        repository.insertLocation(location)
    }
}