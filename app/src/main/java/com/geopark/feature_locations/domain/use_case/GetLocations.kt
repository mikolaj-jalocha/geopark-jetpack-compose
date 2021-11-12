package com.geopark.feature_locations.domain.use_case

import com.geopark.feature_locations.domain.model.Location
import com.geopark.feature_locations.domain.repository.LocationRepository
import com.geopark.feature_locations.domain.util.LocationOrder
import com.geopark.feature_locations.domain.util.LocationType
import com.geopark.feature_locations.domain.util.OrderType
import kotlinx.coroutines.flow.*

class GetLocations(
    private val repository: LocationRepository
) {

    operator fun invoke(
        locationType: LocationType = LocationType.All,
        locationOrder: LocationOrder = LocationOrder.Name(OrderType.Default),
    ): Flow<List<Location>> {

        return repository.getLocations()
            .map { locations ->
                when (locationType) {
                    is LocationType.All -> locations
                    is LocationType.Hotel -> locations.filter { it.type == "Hotel" }
                    is LocationType.Explore -> locations.filter { it.type == "Explore" }
                    is LocationType.Active -> locations.filter { it.type == "Active" }
                    is LocationType.Restaurant -> locations.filter { it.type == "Restaurant" }
                }
            }
            .map { locations ->
                when (locationOrder.orderType) {
                    is OrderType.Ascending -> locations.sortedBy { it.name.lowercase() }
                    is OrderType.Descending -> locations.sortedByDescending { it.name.lowercase() }
                    // TODO: Add sorting by certificate
                    is OrderType.CertificatedFirst -> locations
                    is OrderType.Default -> locations
                }
            }
    }
}

