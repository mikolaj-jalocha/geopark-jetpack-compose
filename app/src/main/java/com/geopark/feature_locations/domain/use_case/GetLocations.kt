package com.geopark.feature_locations.domain.use_case

import com.geopark.feature_locations.domain.model.Location
import com.geopark.feature_locations.domain.repository.LocationRepository
import com.geopark.feature_locations.domain.util.LocationOrder
import com.geopark.feature_locations.domain.util.LocationType
import com.geopark.feature_locations.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetLocations(
    private val repository: LocationRepository
) {

     operator fun invoke(
        locationType: LocationType = LocationType.All
    ): Flow<List<Location>> {

         return repository.getLocations().map { locations ->
             when (locationType) {
                    is LocationType.All -> locations
                    is LocationType.Hotel -> locations.filter { it.type == "Hotel" }
                    is LocationType.Place -> locations.filter { it.type == "Place" }
                    is LocationType.Active -> locations.filter { it.type == "Active" }
                    is LocationType.Restaurant -> locations.filter { it.type == "Restaurant" }
             }
         }

    }
}