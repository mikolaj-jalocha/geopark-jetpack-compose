package com.geopark.feature_locations_events.domain.use_case.locations


import com.geopark.feature_locations_events.data.local.model.Location
import com.geopark.feature_locations_events.domain.repository.LocationRepository

class GetLocationByIdUseCase(
   val repository: LocationRepository
) {

    suspend operator fun invoke(locationId: String) = repository.getLocationById(locationId) ?: Location()

}
