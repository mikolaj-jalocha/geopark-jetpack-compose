package com.geopark.feature_locations_events.domain.use_case.locations

import com.geopark.feature_locations_events.domain.repository.LocationRepository

class GetAllLocationsUseCase(
    private val repository: LocationRepository
){
    operator fun invoke() = repository.getLocationsFlow()
}
