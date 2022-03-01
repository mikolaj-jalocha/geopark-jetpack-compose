package com.geopark.feature_locations_events.domain.use_case.locations

import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import com.geopark.core.util.Resource
import com.geopark.feature_locations_events.data.local.model.Location
import com.geopark.feature_locations_events.domain.util.LocationType
import com.geopark.feature_locations_events.domain.util.SortType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetFilteredLocationsUseCase(
    private val getLocationsByType: GetLocationsByTypeUseCase,
    private val getOrderedLocationsUseCase: GetOrderedLocationsUseCase,
) {

    operator fun invoke(
        searchQuery: String,
        locationType: LocationType
    ) = filterLocations(getLocationsByType(locationType), searchQuery)

    operator fun invoke(
        searchQuery: String,
        locationType: LocationType,
        sortType: SortType
    ) = filterLocations(getOrderedLocationsUseCase(sortType, locationType), searchQuery)

    private fun filterLocations(data: Flow<Resource<List<Location>>>, searchQuery: String) =
        data.map { result ->
            val filteredResult = if (searchQuery.isNotBlank())
                result.data.filter { location ->
                    location.location.name.toLowerCase(Locale.current)
                        .contains(searchQuery.toLowerCase(Locale.current))

                }
            else
                result.data

            if (result is Resource.Success)
                return@map Resource.Success(filteredResult)
            else
                return@map Resource.Loading(filteredResult)
        }
}