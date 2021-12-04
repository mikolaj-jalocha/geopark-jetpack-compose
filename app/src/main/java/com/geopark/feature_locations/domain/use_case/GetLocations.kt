package com.geopark.feature_locations.domain.use_case

import com.geopark.core.util.Resource
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
    ): Flow<Resource<List<Location>>> {

        return repository.getLocations()
            .onEach { locations ->
                when (locationType) {
                    is LocationType.All -> locations.data
                    is LocationType.Hotel -> locations.data?.filter { it.type == "Hotel" }
                    is LocationType.Explore -> locations.data?.filter { it.type == "Explore" }
                    is LocationType.Active -> locations.data?.filter { it.type == "Active" }
                    is LocationType.Restaurant -> locations.data?.filter { it.type == "Restaurant" }
                }
            }
            .onEach { locations ->
                when (locationOrder.orderType) {
                    is OrderType.Ascending -> locations.data?.sortedBy { it.name.lowercase() }
                    is OrderType.Descending -> locations.data?.sortedByDescending { it.name.lowercase() }
                    // TODO: Add sorting by certificate
                    is OrderType.CertificatedFirst -> locations.data
                    is OrderType.Default -> locations.data
                }
            }
    }
}

