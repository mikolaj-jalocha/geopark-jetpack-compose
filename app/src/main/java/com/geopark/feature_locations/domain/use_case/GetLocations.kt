package com.geopark.feature_locations.domain.use_case

import android.util.Log
import com.geopark.core.util.Resource
import com.geopark.feature_locations.domain.model.Location
import com.geopark.feature_locations.domain.repository.LocationRepository
import com.geopark.feature_locations.domain.util.LocationOrder
import com.geopark.feature_locations.domain.util.LocationType
import com.geopark.feature_locations.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class GetLocations(
    private val repository: LocationRepository
) {
 val TAG = "GET_LOCATIONS"
    operator fun invoke(
        locationType: LocationType,
        locationOrder: LocationOrder = LocationOrder.Name(OrderType.Default),
    ): Flow<Resource<List<Location>>> {

        return repository.getLocations()
            .onEach { result ->
                if (result.data != null) {
                        when (locationType) {
                            is LocationType.All -> result.data
                            is LocationType.Hotel -> result.data.filter { it.type == "Hotel" }
                            is LocationType.Explore -> result.data.filter { it.type == "Explore" }
                            is LocationType.Active -> result.data.filter { it.type == "Active" }
                            is LocationType.Restaurant -> result.data.filter { it.type == "Restaurant" }
                        }
                        when (locationOrder.orderType) {
                            is OrderType.Ascending -> result.data.sortedBy { it.name.lowercase() }
                            is OrderType.Descending -> result.data.sortedByDescending { it.name.lowercase() }
                            // TODO: Add sorting by certificate
                            is OrderType.CertificatedFirst -> result.data
                            is OrderType.Default -> result.data
                        }
                }
            }
    }

}


