package com.geopark.feature_locations_events.domain.use_case.locations

import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import com.geopark.core.util.Resource
import com.geopark.feature_locations_events.domain.repository.LocationRepository
import com.geopark.feature_locations_events.domain.util.LocationOrder
import com.geopark.feature_locations_events.domain.util.LocationType
import com.geopark.feature_locations_events.domain.util.OrderType
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class GetLocations(
    private val repository: LocationRepository,
) {
    operator fun invoke(
        locationType: LocationType,
        locationOrder: LocationOrder = LocationOrder.Name(OrderType.Default),
        nameQuery: String = ""
    ) = flow {
        repository.getLocations().collect { result ->
            when (locationType) {
                is LocationType.All -> result.data
                is LocationType.Hotel -> result.data.filter { location ->
                    location.categories.map { it.categoryId }
                        .contains(LocationType.Hotel.toString())
                }
                is LocationType.Restaurant -> result.data.filter { location ->
                    location.categories.map { it.categoryId }
                        .contains(LocationType.Restaurant.toString())
                }
                is LocationType.Explore -> result.data.filter { true }
                is LocationType.Active -> result.data.filter { location ->
                    location.categories.map { it.categoryId }
                        .contains(LocationType.Active.toString())
                }

            }.let { list ->
                if (nameQuery.isNotBlank())
                    list.filter {
                        it.location.name.toLowerCase(Locale.current)
                            .contains(nameQuery.toLowerCase(Locale.current))
                    }
                else
                    list
            }.let { list ->
                when (locationOrder.orderType) {
                    is OrderType.Ascending -> list.sortedBy { it.location.name.lowercase() }
                    is OrderType.Descending -> list.sortedByDescending { it.location.name.lowercase() }
                    // TODO: Add sorting by certificate
                    is OrderType.CertificatedFirst -> list.filter { location ->
                        location.labels.map { it.labelId }.contains("certyfikat")
                    }
                    is OrderType.Default -> list
                }
            }.also { res ->
                when (result) {
                    is Resource.Success -> {
                        emit(Resource.Success(data = res))
                    }
                    is Resource.Loading -> {
                        emit(Resource.Loading(data = res))
                    }
                    is Resource.Error -> {
                        emit(Resource.Error(data = res, message = result.message ?: ""))
                    }
                }
            }
        }
    }
}





